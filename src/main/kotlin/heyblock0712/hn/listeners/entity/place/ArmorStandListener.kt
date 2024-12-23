package heyblock0712.hn.listeners.entity.place

import org.bukkit.entity.ArmorStand
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractAtEntityEvent

class ArmorStandListener : Listener {

    @EventHandler
    fun onArmorStand(event: PlayerInteractAtEntityEvent) {
        val entity = event.rightClicked

        if (entity !is ArmorStand) return
        val player = event.player
        val pos = event.clickedPosition

        player.sendMessage("你點擊了盔甲座 位置 $pos")

    }
}