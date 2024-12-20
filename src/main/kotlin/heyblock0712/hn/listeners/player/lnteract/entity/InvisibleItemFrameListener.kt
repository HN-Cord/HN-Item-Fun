package heyblock0712.hn.listeners.player.lnteract.entity

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.ItemFrame
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.potion.PotionType
import kotlin.random.Random

class InvisibleItemFrameListener: Listener {
    @EventHandler
    fun onHideItemFrame(e: PlayerInteractEntityEvent) {
        val player = e.player
        val entity = e.rightClicked

        if (entity !is ItemFrame || !player.isSneaking) return; // 蹲下 互動
        if (!entity.isVisible) return // 隱形狀態

        val item = player.inventory.itemInMainHand
        if (item.type != Material.POTION) return

        val potionMeta = item.itemMeta as? PotionMeta
        potionMeta?.let {
            if (it.basePotionType != PotionType.LONG_INVISIBILITY) return

            entity.isVisible = false
            player.inventory.setItemInMainHand(null)

            val distance = player.location.distance(entity.location)
            val volume = maxOf(1.0f, distance.toFloat() / 10.0f, 0.0f)
            val pitch = Random.nextFloat() * 2.0f
            player.playSound(player.location, Sound.ENTITY_ITEM_FRAME_ADD_ITEM, volume, pitch)

            e.isCancelled = true
        }
    }

    @EventHandler
    fun onShowItemFrame(e: PlayerInteractEntityEvent) {
        val player = e.player
        val entity = e.rightClicked

        if (entity !is ItemFrame || !player.isSneaking) return;

        if (entity.isVisible) return

        val item = player.inventory.itemInMainHand
        if (item.type != Material.WATER_BUCKET) return

        entity.isVisible = true

        val distance = player.location.distance(entity.location)
        val volume = maxOf(1.0f, distance.toFloat() / 10.0f, 0.0f)
        val pitch = Random.nextFloat() * 2.0f
        player.playSound(player.location, Sound.ENTITY_ITEM_FRAME_REMOVE_ITEM, volume, pitch)

        e.isCancelled = true
    }
}