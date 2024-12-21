package heyblock0712.hn.data.block

import heyblock0712.hn.data.ItemIO
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemRarity
import org.bukkit.inventory.ItemStack

class User : ItemIO {
    override val id: String = "user"
    override val item: ItemStack = ItemStack(Material.DISPENSER)

    override fun craft() {
        val meta = item.itemMeta
        meta.displayName(Component.translatable("hn.block.user", "使用者"))
        meta.setRarity(ItemRarity.RARE)
        item.itemMeta = meta
    }
}