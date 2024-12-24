package heyblock0712.hn.listeners.player.lnteract.click

import heyblock0712.hn.inventory.InventoryGUI
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class GUIListener: Listener {
    @EventHandler
    fun onGUI(event: InventoryClickEvent) {
        val holder = event.inventory.holder

        if (holder is InventoryGUI) {
            holder.handleGUI(event)
        }
    }
}