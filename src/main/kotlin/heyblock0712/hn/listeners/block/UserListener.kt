package heyblock0712.hn.listeners.block

import com.destroystokyo.paper.MaterialTags
import heyblock0712.hn.data.block.User
import heyblock0712.hn.events.HNBlockPlacerEvent
import heyblock0712.hn.utils.PDC
import heyblock0712.hn.utils.getPersistentData
import heyblock0712.hn.utils.setPersistentData
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.block.Container
import org.bukkit.block.Dispenser
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockDispenseEvent

class UserListener : Listener {
    @EventHandler
    fun onHNPlacer(event: HNBlockPlacerEvent) {
        val id = event.id
        if (id != User.id) return

        val block = event.block
        val key = NamespacedKey("hn", "mode")

        val modeID = User.Mode.Place.type
        val modeName = User.Mode.Place.display

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
    fun onDispense(event: BlockDispenseEvent) {
        val block = event.block
        val id = getPersistentData(block, PDC.ID.key)?: return
        if (id != User.id) return

        val item = event.item
        if (!item.type.isBlock) return

        val state = block.state
        if (state !is Dispenser) return
        val blockData = state.blockData as? org.bukkit.block.data.type.Dispenser
        blockData?.let {
            val facing = it.facing

            val placeLog = block.location.add(facing.direction)

            if (placeLog.block.type != Material.AIR) return

            placeLog.block.type = item.type

            if ((item.amount - 1) == 0) {
                event.isCancelled = true
                val inventory = state.inventory
                inventory.removeItem(item)
            }
        }
    }
}