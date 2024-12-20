package heyblock0712.hn.data

import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

interface ItemIO {
    val id: String
    val item : ItemStack

    fun craft()

    fun init() {
        val item = item
        val meta = item.itemMeta

        val key = NamespacedKey("hn", "item_id")
        meta.persistentDataContainer.set(key, PersistentDataType.STRING, id)

        item.setItemMeta(meta)
    }
}