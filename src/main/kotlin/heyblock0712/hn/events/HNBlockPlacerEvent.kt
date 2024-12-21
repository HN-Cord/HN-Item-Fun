package heyblock0712.hn.events

import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.bukkit.inventory.ItemStack

class HNBlockPlacerEvent (
    val player : Player,
    val id : String,
    val itemStack: ItemStack,
    val block: Block
): Event() {

    companion object{
        private val handlerList = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList = handlerList
    }

    override fun getHandlers(): HandlerList = handlerList
}