package heyblock0712.hn.inventory

import heyblock0712.hn.utils.gui.PlayerGUI
import heyblock0712.hn.utils.setPersistentData
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack

abstract class InventoryGUI(open val playerGUI: PlayerGUI) : InventoryHolder {

    private lateinit var inv : Inventory

    companion object {
        val GUI_ID = NamespacedKey("hn", "gui_id")
    }

    abstract fun slots() : Int
    abstract fun title() : Component
    abstract fun handleGUI(event: InventoryClickEvent)
    abstract fun setGUIItem()

    fun open() {
        inv = Bukkit.createInventory(this, slots(), title())
        setGUIItem()

        playerGUI.player.openInventory(inv)
    }

    fun close() {

    }

    override fun getInventory(): Inventory {
        return inv
    }

    fun craftGUIItem(id: String, material: Material, amount: Int) : ItemStack {
        val item = ItemStack(material, amount)
        setPersistentData(item, GUI_ID, id)
        return item
    }

    fun craftGUIItem(id: String, material: Material) : ItemStack {
        return craftGUIItem(id, material, 1)
    }

    fun createFillItem(material: Material) : ItemStack {
        val item = craftGUIItem("fill", material)
        val meta = item.itemMeta
        meta.displayName(Component.text(" "))
        item.itemMeta = meta
        return item
    }
}