package heyblock0712.hn.utils

import org.bukkit.NamespacedKey
import org.bukkit.block.Block
import org.bukkit.block.TileState
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

fun hasPersistentData(item: ItemStack, key: NamespacedKey): Boolean {
    val itemMeta = item.itemMeta
    return itemMeta != null && itemMeta.persistentDataContainer.has(key, PersistentDataType.STRING)
}

fun getPersistentData(item: ItemStack, key: NamespacedKey): String? {
    val itemMeta = item.itemMeta
    return if (itemMeta != null && itemMeta.persistentDataContainer.has(key, PersistentDataType.STRING)) {
        itemMeta.persistentDataContainer.get(key, PersistentDataType.STRING)
    } else {
        null
    }
}

fun setPersistentData(item: ItemStack, key: NamespacedKey, value: String) {
    val itemMeta = item.itemMeta
    itemMeta?.let {
        it.persistentDataContainer.set(key, PersistentDataType.STRING, value)
        item.itemMeta = it
    }
}

fun hasPersistentContainer(block: Block): Boolean {
    return block.state is TileState
}

fun hasPersistentData(block: Block, key: NamespacedKey): Boolean {
    if (!hasPersistentContainer(block)) return false
    val tileState = block.state as TileState
    return tileState.persistentDataContainer.has(key, PersistentDataType.STRING)
}

fun getPersistentData(block: Block, key: NamespacedKey): String? {
    if (!hasPersistentContainer(block)) return null
    val tileState = block.state as TileState
    return tileState.persistentDataContainer.get(key, PersistentDataType.STRING)
}

fun setPersistentData(block: Block, key: NamespacedKey, value: String) {
    if (!hasPersistentContainer(block)) return
    val tileState = block.state as TileState
    tileState.persistentDataContainer.set(key, PersistentDataType.STRING, value)
    tileState.update() // 更改到世界
}

enum class PDC(val key: NamespacedKey) {
    ID(NamespacedKey("hn", "id"));
}