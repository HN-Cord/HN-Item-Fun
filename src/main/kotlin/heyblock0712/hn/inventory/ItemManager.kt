package heyblock0712.hn.inventory

import org.bukkit.inventory.ItemStack

object ItemManager {
    private val items : MutableMap<String , ItemIO> = HashMap()

    fun registerItem(item: ItemIO) {
        item.init()
        item.craft()
        items[item.id] = item
    }

    fun getItem(id: String): ItemIO? {
        return items[id]
    }

    fun getItemStack(id: String): ItemStack? {
        val item = getItem(id) ?: return null
        return item.item.clone()
    }

    fun getItems(): Map<String, ItemIO> {
        return items
    }
}