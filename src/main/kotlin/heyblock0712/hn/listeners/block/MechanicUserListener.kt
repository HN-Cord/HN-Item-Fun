package heyblock0712.hn.listeners.block

import heyblock0712.hn.inventory.item.MechanicUser
import heyblock0712.hn.events.HNBlockPlacerEvent
import heyblock0712.hn.inventory.gui.MechanicUserGUI
import heyblock0712.hn.utils.*
import heyblock0712.hn.utils.gui.PlayerGUI
import io.papermc.paper.event.block.BlockPreDispenseEvent
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.block.Container
import org.bukkit.block.Dispenser
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

class MechanicUserListener : Listener {
    // 放置事件
    @EventHandler
    fun onHNPlacer(event: HNBlockPlacerEvent) {
        val id = event.id
        if (id != MechanicUser.ID) return

        val block = event.block

        val modeKey = MechanicUser.MODE
        val mode = MechanicUser.Mode.BREAK
        val modeID = mode.type
        val modeName = mode.display

        val actionKey = MechanicUser.ACTION
        val action = MechanicUser.Action.NO
        val actionID = action.type

        val state = block.state
        if (state is Container) {
            val name = state.customName()?.append(
                Component.text(" (").append(modeName).append(Component.text(")"))
            )

            state.customName(name)
            state.update()
        }

        setPersistentData(block, modeKey, modeID)
        setPersistentData(block, actionKey, actionID)
    }

    // GUI 事件
    @EventHandler
    fun onPlayerClick(event: PlayerInteractEvent) {
        val block = event.clickedBlock ?: return
        val id = getPersistentData(block, PDC.ID.key) ?: return
        if (id != MechanicUser.ID) return

        val player = event.player
        if (!player.isSneaking) return

        val dispenser = block.state
        if (dispenser !is Dispenser) return
        val blockData = dispenser.blockData as? org.bukkit.block.data.type.Dispenser
        blockData?.let {
            val facing = it.facing
            val target = block.location.add(facing.direction)

            val modeString = getPersistentData(block, MechanicUser.MODE) ?: return
            val mode = MechanicUser.Mode.fromType(modeString) ?: return

            val actionString = getPersistentData(block, MechanicUser.ACTION) ?: return
            val action = MechanicUser.Action.fromType(actionString) ?: return
            val mechanicUser =
                heyblock0712.hn.block.type.MechanicUser(dispenser, blockData, target.block, mode, action)

            event.isCancelled = true

            val playerGUI = PlayerGUI(player)
            val gui = MechanicUserGUI(playerGUI)
            playerGUI.state = mechanicUser
            gui.open()
        }
    }

    // 分配事件
    @EventHandler
    fun onDispense(event: BlockPreDispenseEvent) {
        val block = event.block
        val id = getPersistentData(block, PDC.ID.key)?: return
        if (id != MechanicUser.ID) return
        val item = event.itemStack

        // 檢查漏斗狀態
        val dispenser = block.state
        if (dispenser !is Dispenser) return
        val blockData = dispenser.blockData as? org.bukkit.block.data.type.Dispenser
        blockData?.let {
            val facing = it.facing
            val target = block.location.add(facing.direction)

            val modeString = getPersistentData(block, MechanicUser.MODE)?: return
            val mode = MechanicUser.Mode.fromType(modeString) ?: return

            val actionString = getPersistentData(block, MechanicUser.ACTION) ?: return
            val action = MechanicUser.Action.fromType(actionString) ?: return
            val mechanicUser = heyblock0712.hn.block.type.MechanicUser(dispenser, blockData, target.block, mode, action, item)

            when (mode) {
                MechanicUser.Mode.PLACE -> onPlaceMode(event, mechanicUser)
                MechanicUser.Mode.BREAK -> onBreakMode(event, mechanicUser)
            }
        }
    }

    private fun onPlaceMode(event: BlockPreDispenseEvent ,mechanicUser: heyblock0712.hn.block.type.MechanicUser) {
        val itemStack = mechanicUser.itemStack?: return
        val target = mechanicUser.target
        val dispenser = mechanicUser.dispenser
        if (!itemStack.type.isBlock) return
        if (target.type != Material.AIR) return

        target.type = itemStack.type

        val inventory = dispenser.inventory
        for (i in inventory.contents.indices) {
            val invItem = inventory.contents[i]

            if (invItem != null && invItem.isSimilar(itemStack)) {
                if (invItem.amount > 1) {
                    invItem.amount -= 1
                } else {
                    inventory.setItem(i, ItemStack(Material.AIR))
                }
                break
            }
        }

        event.isCancelled = true
    }

    private fun onBreakMode(event: BlockPreDispenseEvent, mechanicUser: heyblock0712.hn.block.type.MechanicUser) {
        val itemStack = mechanicUser.itemStack?: return
        val target = mechanicUser.target

        val check = target.breakNaturally(itemStack, true)
        if (check) {
            val tool = ToolMechanicsUtils.applyToolDurabilityDamage(itemStack)
            if (tool) mechanicUser.playToolBreakSound()
        } else {
            mechanicUser.playDispenseFailSound()
        }

        event.isCancelled = true
    }
}