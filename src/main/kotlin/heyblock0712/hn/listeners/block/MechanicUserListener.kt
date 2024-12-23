package heyblock0712.hn.listeners.block

import heyblock0712.hn.data.block.MechanicUser
import heyblock0712.hn.events.HNBlockPlacerEvent
import heyblock0712.hn.utils.PDC
import heyblock0712.hn.utils.ToolMechanicsUtils
import heyblock0712.hn.utils.getPersistentData
import heyblock0712.hn.utils.setPersistentData
import io.papermc.paper.event.block.BlockPreDispenseEvent
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.block.Container
import org.bukkit.block.Dispenser
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack

class MechanicUserListener : Listener {
    @EventHandler
    fun onHNPlacer(event: HNBlockPlacerEvent) {
        val id = event.id
        if (id != MechanicUser.id) return

        val block = event.block
        val key = MechanicUser.MODE

        val mode = MechanicUser.Mode.Break
        val modeID = mode.type
        val modeName = mode.display

        val state = block.state
        if (state is Container) {
            val name = state.customName()?.append(
                Component.text(" (").append(modeName).append(Component.text(")"))
            )

            state.customName(name)
            state.update()
        }

        setPersistentData(block, key, modeID)
    }

    @EventHandler
    fun onDispense(event: BlockPreDispenseEvent) {
        val block = event.block
        val id = getPersistentData(block, PDC.ID.key)?: return
        if (id != MechanicUser.id) return
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
            val mechanicUser = heyblock0712.hn.block.MechanicUser(dispenser, blockData, target.block, mode, item)

            when (mode) {
                MechanicUser.Mode.Place -> onPlaceMode(event, mechanicUser)
                MechanicUser.Mode.Break -> onBreakMode(event, mechanicUser)
            }
        }
    }

    private fun onPlaceMode(event: BlockPreDispenseEvent ,mechanicUser: heyblock0712.hn.block.MechanicUser) {
        val itemStack = mechanicUser.itemStack
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

    private fun onBreakMode(event: BlockPreDispenseEvent, mechanicUser: heyblock0712.hn.block.MechanicUser) {
        val itemStack = mechanicUser.itemStack
        val target = mechanicUser.target

        val check = target.breakNaturally(itemStack, true)
        if (check) {
            val tool = ToolMechanicsUtils.applyToolDurabilityDamage(itemStack)
            if (tool) mechanicUser.playToolBreakSound()
        }

        event.isCancelled = true
    }
}