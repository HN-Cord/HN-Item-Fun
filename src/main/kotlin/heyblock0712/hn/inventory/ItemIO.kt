package heyblock0712.hn.inventory

import heyblock0712.hn.utils.PDC
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

interface ItemIO {
    val id: String
    val item : ItemStack

    fun craft()

    fun init() {
        val item = item
        val meta = item.itemMeta

        val key = PDC.ID.key
        meta.persistentDataContainer.set(key, PersistentDataType.STRING, id)

        item.setItemMeta(meta)
    }
}