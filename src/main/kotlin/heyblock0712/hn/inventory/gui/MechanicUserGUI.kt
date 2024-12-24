package heyblock0712.hn.inventory.gui

import heyblock0712.hn.block.type.MechanicUser
import heyblock0712.hn.inventory.InventoryGUI
import heyblock0712.hn.inventory.item.MechanicUser.Mode.*
import heyblock0712.hn.utils.getPersistentData
import heyblock0712.hn.utils.gui.PlayerGUI
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemFlag

class MechanicUserGUI(override val playerGUI: PlayerGUI) : InventoryGUI(playerGUI) {
    val FILLITEM = createFillItem(Material.GRAY_STAINED_GLASS_PANE)

    override fun slots(): Int {
        return 27
    }

    override fun title(): Component {
        return Component.translatable("hn.gui.mechanic_user.title", "機械用戶 介面")
    }

    override fun handleGUI(event: InventoryClickEvent) {
        val clickItem = event.currentItem ?: return
        val guiID = getPersistentData(clickItem, GUI_ID) ?: return

        playerGUI.player.sendMessage("你點擊了 $guiID")
        event.isCancelled = true
    }

    override fun setGUIItem() {

        for (i in 0 until inventory.size) {
            inventory.setItem(i, FILLITEM)
        }

        // 模式
        setModeItem()

        // 動作
        val noActionItem = craftGUIItem("no_action", Material.BARRIER)
        val noActionMeta = noActionItem.itemMeta
        noActionMeta.displayName(Component.text("不進行任何操作", NamedTextColor.WHITE))
        noActionItem.itemMeta = noActionMeta
        inventory.setItem(8, noActionItem)

        val throwItem = craftGUIItem("throw_action", Material.DROPPER)
        val throwMeta = throwItem.itemMeta
        throwMeta.displayName(Component.text("投擲出去", NamedTextColor.WHITE))
        throwItem.itemMeta = throwMeta
        inventory.setItem(17, throwItem)

        val damageItem = craftGUIItem("damage_action", Material.LAVA_BUCKET)
        val damageMeta = damageItem.itemMeta
        damageMeta.displayName(Component.text("損壞物品", NamedTextColor.WHITE))
        damageItem.itemMeta = damageMeta
        inventory.setItem(26, damageItem)
    }

    private fun setModeItem() {
        val state = playerGUI.state
        System.out.println("通過1")
        if (state !is MechanicUser) return
        System.out.println("通過2")

        val placeItem = craftGUIItem("place_mode", Material.BRICKS)
        val placeMeta = placeItem.itemMeta
        placeMeta.displayName(Component.text("放置", NamedTextColor.WHITE))

        val breakItem = craftGUIItem("break_mode", Material.DIAMOND_PICKAXE)
        val breakMeta = breakItem.itemMeta
        breakMeta.displayName(Component.text("破壞", NamedTextColor.WHITE))

        val killItem = craftGUIItem("kill_mode", Material.DIAMOND_SWORD)
        val killMeta = killItem.itemMeta
        killMeta.displayName(Component.text("擊殺", NamedTextColor.WHITE))

        val useItem = craftGUIItem("use_mode", Material.FLINT_AND_STEEL)
        val useMeta = useItem.itemMeta
        useMeta.displayName(Component.text("使用", NamedTextColor.WHITE))

        when (state.mode) {
            BREAK -> {
                breakMeta.addEnchant(Enchantment.UNBREAKING, 1, true)
                breakMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
            }
            PLACE -> {
                placeMeta.addEnchant(Enchantment.UNBREAKING, 1, true)
                placeMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
            }
        }

        placeItem.itemMeta = placeMeta
        breakItem.itemMeta = breakMeta
        killItem.itemMeta = killMeta
        useItem.itemMeta = useMeta

        inventory.setItem(11, placeItem)
        inventory.setItem(12, breakItem)
        inventory.setItem(13, killItem)
        inventory.setItem(14, useItem)
    }
}