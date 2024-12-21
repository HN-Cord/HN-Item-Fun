package heyblock0712.hn.listeners

import heyblock0712.hn.listeners.block.HNBlockListener
import heyblock0712.hn.listeners.block.UserListener
import heyblock0712.hn.listeners.entity.place.ArmorStandListener
import heyblock0712.hn.listeners.player.lnteract.entity.InvisibleItemFrameListener
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin

object Listener {
    fun init(event: PluginManager, plugin: JavaPlugin) {
        event.registerEvents(HNBlockListener(), plugin)
        event.registerEvents(UserListener(), plugin)

        event.registerEvents(InvisibleItemFrameListener(), plugin)
        event.registerEvents(ArmorStandListener(), plugin)
    }
}