package heyblock0712.hn

import de.tr7zw.changeme.nbtapi.NBT
import org.bukkit.plugin.java.JavaPlugin

class HNItemFun : JavaPlugin() {

    override fun onEnable() {
        logger.info("正在啟動 HNItemFun...")

        if (!NBT.preloadApi()) {
            logger.warning("未加載 NBT API 將不啟動此插件")
            server.pluginManager.disablePlugin(this)
            return
        }

        logger.info("啟動完畢!")
    }

    override fun onDisable() {
        logger.warning("正在卸載 HNItemFun...")

        logger.info("卸載完畢!")
    }
}
