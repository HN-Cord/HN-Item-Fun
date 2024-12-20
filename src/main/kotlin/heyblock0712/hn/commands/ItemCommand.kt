@file:JvmName("ItemCommand")

package heyblock0712.hn.commands

import heyblock0712.hn.HNItemFun.Companion.commandManager
import hn.blacknight0981.hNLib.commands.HNCommandIO
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.CommandSender
import java.util.*

class ItemCommand : HNCommandIO{
    override fun getName(): String = "item"

    override fun execute(sender: CommandSender, arge: Array<out String>): Boolean {
        if(arge.isEmpty()) {
            sender.sendMessage(Component.text("請提供一個子命令", NamedTextColor.RED))
            return true
        }

        val subCommand = arge[0]
        val subArgs = arrayOfNulls<String>(arge.size - 1)
        System.arraycopy(arge, 1, subArgs, 0, arge.size)

        return commandManager!!.execute(subCommand, sender, arge)
    }

    override fun tabComplete(sender: CommandSender, args: Array<out String>): MutableList<String> {
        if (args.size == 1) {
            val subStrings: MutableList<String> = ArrayList()
            val partial = args[0].lowercase(Locale.getDefault())
            for (subCommand in commandManager!!.commands.keys) {
                if (subCommand.startsWith(partial)) {
                    subStrings.add(subCommand)
                }
            }
            return subStrings
        } else if (args.size > 1) {
            // 交給 子命令參數
            val commandIO = commandManager!!.commands[args[0].lowercase(Locale.getDefault())]
            if (commandIO != null) {
                val subStrings = arrayOfNulls<String>(args.size - 1)
                System.arraycopy(args, 1, subStrings, 0, subStrings.size)
                return commandIO.tabComplete(sender, subStrings)
            }
        }
        return mutableListOf()
    }
}