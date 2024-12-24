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

        val MODE = NamespacedKey(ID, "mode")
        val ACTION = NamespacedKey(ID, "action")
    }

    enum class Mode(val type: String, val display : Component) {
        BREAK("break", Component.translatable("hn.mechanic_user.mode.break", "破壞")),
        PLACE("place", Component.translatable("hn.mechanic_user.mode.place", "放置"));

        companion object {
            fun fromType(type: String): Mode? {
                return entries.find { it.type == type }
            }
        }
    }

    enum class Action(val type: String, val display : Component) {
        NO("no", Component.translatable("hn.mechanic_user.action.no", "不進行任何動作")),
        THROW("throw", Component.translatable("hn.mechanic_user.action.throw", "丟棄")),
        DAMAGE("damage", Component.translatable("hn.mechanic_user.action.damage", "直至損壞"));

        companion object {
            fun fromType(type: String): Action? {
                return Action.entries.find { it.type == type }
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