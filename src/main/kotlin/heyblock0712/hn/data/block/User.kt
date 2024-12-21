package heyblock0712.hn.data.block

import heyblock0712.hn.data.ItemIO
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemRarity
import org.bukkit.inventory.ItemStack

class User : ItemIO {
    companion object {
        var id: String = "user"
        val displayName = Component.translatable("hn.block.user", "使用者")

    }

    enum class Mode(val type: String, val display : Component) {
        Break("break", Component.translatable("hn.mode.break", "破壞")),
        Place("place", Component.translatable("hn.mode.place", "放置"));
    }

    override val id: String = User.id

    override val item: ItemStack = ItemStack(Material.DISPENSER)

    override fun craft() {
        val meta = item.itemMeta
        meta.displayName(displayName)
        meta.setRarity(ItemRarity.RARE)
        item.itemMeta = meta
    }
}