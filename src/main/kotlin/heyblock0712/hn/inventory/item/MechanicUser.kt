package heyblock0712.hn.inventory.item

import heyblock0712.hn.inventory.ItemIO
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemRarity
import org.bukkit.inventory.ItemStack

class MechanicUser : ItemIO {
    companion object {
        const val ID: String = "mechanic_user"
        val DISPLAY_NAME = Component.translatable("hn.block.mechanic_user", "機械用戶")

        val MODE = NamespacedKey("hn", "mode")
    }

    enum class Mode(val type: String, val display : Component) {
        BREAK("break", Component.translatable("hn.mode.break", "破壞")),
        PLACE("place", Component.translatable("hn.mode.place", "放置"));

        companion object {
            fun fromType(type: String): Mode? {
                return entries.find { it.type == type }
            }
        }
    }

    override val id: String = ID

    override val item: ItemStack = ItemStack(Material.DISPENSER)

    override fun craft() {
        val meta = item.itemMeta
        meta.displayName(DISPLAY_NAME)
        meta.setRarity(ItemRarity.RARE)
        item.itemMeta = meta
    }
}