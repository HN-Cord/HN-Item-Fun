package heyblock0712.hn.listeners.block

import heyblock0712.hn.data.block.User
import heyblock0712.hn.events.HNBlockPlacerEvent
import heyblock0712.hn.utils.PDC
import heyblock0712.hn.utils.getPersistentData
import heyblock0712.hn.utils.setPersistentData
import io.papermc.paper.event.block.BlockPreDispenseEvent
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.Container
import org.bukkit.block.Dispenser
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack

class UserListener : Listener {
    @EventHandler
    fun onHNPlacer(event: HNBlockPlacerEvent) {
        val id = event.id
        if (id != User.id) return

        val block = event.block
        val key = User.MODE

        val mode = User.Mode.Place
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
        if (id != User.id) return

        val item = event.itemStack
        if (!item.type.isBlock) return

        val state = block.state
        if (state !is Dispenser) return
        val blockData = state.blockData as? org.bukkit.block.data.type.Dispenser
        blockData?.let {
            val facing = it.facing

            val placeLog = block.location.add(facing.direction)

            if (placeLog.block.type != Material.AIR) return
            val mode = getPersistentData(block, User.MODE)?: return

            if (mode == User.Mode.Place.type) onPlaceMode(placeLog.block, item, state)

            event.isCancelled = true
        }
    }

    private fun onPlaceMode(block: Block, itemStack: ItemStack, dispenser: Dispenser) {
        block.type = itemStack.type

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
    }
}