package heyblock0712.hn.listeners.entity.place

import org.bukkit.Material
import org.bukkit.entity.ArmorStand
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityPlaceEvent

class ArmorStandListener : Listener {
    @EventHandler
    fun onArmorStand(event: EntityPlaceEvent) {
        val player = event.player?: return
        val entity = event.entity

        if (entity !is ArmorStand) return

        val item = player.inventory.itemInOffHand
        if (item.type != Material.STICK && item.amount < 3) return

        item.amount -= 2
        entity.setArms(true)
    }
}