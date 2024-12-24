package heyblock0712.hn.block.type

import heyblock0712.hn.inventory.item.MechanicUser
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.block.Dispenser
import org.bukkit.inventory.ItemStack

class MechanicUser(
    val dispenser: Dispenser,
    val dispenserData: org.bukkit.block.data.type.Dispenser,
    val target: Block,
    val mode: MechanicUser.Mode,
    val itemStack: ItemStack,
) {

    fun playDispenseFailSound() {
        dispenser.block.world.playSound(target.location, Sound.BLOCK_DISPENSER_FAIL, 1.0f, 1.0f)
    }

    fun playDispenseSuccessSound() {
        dispenser.block.world.playSound(target.location, Sound.BLOCK_DISPENSER_DISPENSE, 1.0f, 1.0f)
    }

    fun playToolBreakSound() {
        dispenser.block.world.playSound(target.location, Sound.ENTITY_ITEM_BREAK, 1.0f, 0.9f)
    }
}