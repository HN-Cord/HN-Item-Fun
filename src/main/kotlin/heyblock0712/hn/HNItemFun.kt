package heyblock0712.hn

import de.tr7zw.changeme.nbtapi.NBT
import heyblock0712.hn.commands.ItemCommand
import heyblock0712.hn.commands.item.GiveCommand
import heyblock0712.hn.inventory.RegisterItem
import heyblock0712.hn.listeners.Listener
import hn.blacknight0981.hNLib.HNLib
import hn.blacknight0981.hNLib.commands.HNCommandManager
import org.bukkit.plugin.java.JavaPlugin

class HNItemFun : JavaPlugin() {

    companion object {
        var commandManager: HNCommandManager? = null
            private set
    }

    override fun onEnable() {
        logger.info("正在啟動 HNItemFun...")

        if (!NBT.preloadApi()) {
            logger.warning("未加載 NBT API 將不啟動此插件")
            server.pluginManager.disablePlugin(this)
            return
        }

        commandManager = HNCommandManager()
        commandManager?.register(GiveCommand())

        HNLib.getCommandManager().register(ItemCommand())

        RegisterItem.init()
        Listener.init(server.pluginManager, this)

        logger.info("啟動完畢!")
    }

    override fun onDisable() {
        logger.warning("正在卸載 HNItemFun...")

        logger.info("卸載完畢!")
    }
}
