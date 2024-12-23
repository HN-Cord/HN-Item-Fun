package heyblock0712.hn.data.block

import heyblock0712.hn.data.ItemIO
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemRarity
import org.bukkit.inventory.ItemStack

class MechanicUser : ItemIO {
    companion object {
        var id: String = "mechanic_user"
        val displayName = Component.translatable("hn.block.mechanic_user", "機械用戶")

        val MODE = NamespacedKey("hn", "mode")

    }

    enum class Mode(val type: String, val display : Component) {
        Break("break", Component.translatable("hn.mode.break", "破壞")),
        Place("place", Component.translatable("hn.mode.place", "放置"));

        companion object {
            fun fromType(type: String): Mode? {
                return entries.find { it.type == type }
            }
        }
    }

    override val id: String = MechanicUser.id

    override val item: ItemStack = ItemStack(Material.DISPENSER)

    override fun craft() {
        val meta = item.itemMeta
        meta.displayName(displayName)
        meta.setRarity(ItemRarity.RARE)
        item.itemMeta = meta
    }
}