package heyblock0712.hn.inventory

import heyblock0712.hn.utils.gui.PlayerGUI
import org.bukkit.entity.Player

class GUIManager {
    companion object {
        private val playerGUIMap : MutableMap<Player, PlayerGUI> = HashMap()

        fun getPlayerGUI(player: Player) : PlayerGUI {
            return playerGUIMap[player] ?: run {
                val playerGUI = PlayerGUI(player)
                playerGUIMap[player] = playerGUI
                playerGUI
            }
        }
    }
}