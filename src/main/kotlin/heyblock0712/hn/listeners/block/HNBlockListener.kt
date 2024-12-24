package heyblock0712.hn.listeners.block

import heyblock0712.hn.inventory.ItemManager
import heyblock0712.hn.events.HNBlockPlacerEvent
import heyblock0712.hn.utils.PDC
import heyblock0712.hn.utils.getPersistentData
import heyblock0712.hn.utils.setPersistentData
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.Container
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent

class HNBlockListener: Listener {
    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        val player = event.player
        val item = event.itemInHand

        if (item.type == Material.AIR) return

        val key = PDC.ID.key
        val id = getPersistentData(item, key) ?: return

        val block = event.block
        val hnEvent = HNBlockPlacerEvent(player, id, item, block)
        setPersistentData(block, key, id) // 設置 ID

        Bukkit.getPluginManager().callEvent(hnEvent)
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        val player = event.player //Todo: 未來給予自訂事件用的
        val block = event.block

        if (block.type == Material.AIR) return

        val key = PDC.ID.key
        val id = getPersistentData(block, key) ?: return

        event.isDropItems = false

        val state = block.state
        if (state is Container) {
            val inventory = state.inventory

            for (item in inventory.contents) {
                if (item == null || item.type == Material.AIR) continue
                block.world.dropItemNaturally(block.location, item)
            }

            inventory.clear()
        }

        val item = ItemManager.getItemStack(id) ?: return
        block.world.dropItemNaturally(block.location, item)
    }
}