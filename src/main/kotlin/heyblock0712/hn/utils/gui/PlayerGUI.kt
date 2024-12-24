package heyblock0712.hn.utils.gui

import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class PlayerGUI(var player: Player) {
    var state : Any? = null
    var block : Block? = null
    var item : ItemStack? = null

}