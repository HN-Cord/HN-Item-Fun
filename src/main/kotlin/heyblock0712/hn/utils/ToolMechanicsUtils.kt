package heyblock0712.hn.utils

import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import kotlin.random.Random

object ToolMechanicsUtils {
    fun applyToolDurabilityDamage(itemStack: ItemStack): Boolean {
        val itemMeta = itemStack.itemMeta
        if (itemMeta !is Damageable) return false

        val unbreakingLevel = itemStack.getEnchantmentLevel(Enchantment.UNBREAKING)

        val shouldIgnoreDamage = unbreakingLevel > 0 && Random.nextInt(unbreakingLevel + 1) != 0

        if (!shouldIgnoreDamage) {
            // 增加耐久消耗

            itemMeta.damage += 1

            // 检查是否超出工具耐久值
            if (itemMeta.damage >= itemStack.type.maxDurability) {
                // 工具损坏
                itemStack.amount = 0
                return true // 返回损坏状态
            } else {
                // 更新工具元数据
                itemStack.itemMeta = itemMeta
            }
        }

        return false // 工具未损坏
    }
}