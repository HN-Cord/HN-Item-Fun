package heyblock0712.hn.commands.item

import heyblock0712.hn.inventory.ItemManager
import hn.blacknight0981.hNLib.commands.HNCommandIO
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import java.util.*

class GiveCommand: HNCommandIO{
    override fun getName(): String = "give"

    override fun execute(sender: CommandSender, arge: Array<out String>): Boolean {
        if(arge.isEmpty() || arge[0].isBlank()) {
            sender.sendMessage(Component.text("請提供一個玩家ID", NamedTextColor.RED))
            return true
        }

        // player
        val playerName = arge[0]
        val player = Bukkit.getPlayer(playerName)
        if (player == null || !player.isOnline){
            sender.sendMessage(Component.text("你指定的玩家 $playerName 不存在或不再線", NamedTextColor.RED))
            return true
        }

        // item
        if (arge.size < 2 || arge[1].isBlank()){
            sender.sendMessage(Component.text("請提供有效的物品 ID", NamedTextColor.RED))
            return true
        }

        val itemID = arge[1]
        val item = ItemManager.getItemStack(itemID)
        if (item == null) {
            sender.sendMessage(Component.text("無效的物品 ID $itemID", NamedTextColor.RED))
            return true
        }

        val amount = if (arge.size > 2) {
            arge[2].toIntOrNull()?.takeIf { it > 0 } ?: 1
        } else {
            1
        }

        item.amount = amount

        player.inventory.addItem(item)
        val message = Component.text("已給予玩家 ", NamedTextColor.WHITE)
            .append(Component.text(playerName, NamedTextColor.GREEN))
            .append(Component.text(" ", NamedTextColor.WHITE))
            .append(item.displayName())
            .append(Component.text(" x $amount 物品", NamedTextColor.WHITE))

        sender.sendMessage(message)
        return true
    }

    override fun tabComplete(sender: CommandSender, args: Array<out String>): MutableList<String> {
        if (args.size == 1) {
            val subStrings: MutableList<String> = ArrayList()
            val partial = args[0].lowercase(Locale.getDefault())

            for (player in Bukkit.getOnlinePlayers()) {
                if (player.name.lowercase(Locale.getDefault()).startsWith(partial)) {
                    subStrings.add(player.name)
                }
            }
            return subStrings
        } else if (args.size == 2) {
            val subStrings: MutableList<String> = ArrayList()
            val partial = args[1].lowercase(Locale.getDefault())
            for (subCommand in ItemManager.getItems().keys) {
                if (subCommand.startsWith(partial)) {
                    subStrings.add(subCommand)
                }
            }
            return subStrings
        }
        return super.tabComplete(sender, args)
    }
}