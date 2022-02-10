package pl.wolny.klockowamaskaradisguise.master.whitelist

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import pl.wolny.klockowamaskaradisguise.master.sendFormatedMessage

class WhiteListCommand(private val whiteListController: WhiteListController): CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(!sender.hasPermission("klockowamasakra.whitelist")){
            sender.sendFormatedMessage("&cNo permission!")
            return true
        }
        if(args.isEmpty()){
            sender.sendFormatedMessage("&cInvalid usage!")
            return true
        }
        when(args[0]){
            "list" -> {
                val stringBuilder = StringBuilder()
                stringBuilder.append("&aGracze na liście: ")
                whiteListController.listWhitelistedPlayers().forEach{
                    stringBuilder.append("&c$it, ")
                }
                sender.sendFormatedMessage(stringBuilder.toString().trimEnd(' ', ','))
            }
            "add" -> {
                val name = args.getOrNull(1)
                if(name == null){
                    sender.sendFormatedMessage("&cNo player provided!")
                    return true
                }
                whiteListController.addToWhitelist(name)
                sender.sendFormatedMessage("&aSuccess!")
            }
            "remove" -> {
                val name = args.getOrNull(1)
                if(name == null){
                    sender.sendFormatedMessage("&cNo player provided!")
                    return true
                }
                if(!whiteListController.listWhitelistedPlayers().contains(name)){
                    sender.sendFormatedMessage("&cThat player is not whitelisted!")
                    return true
                }
                whiteListController.removeFromWhiteList(name)
                sender.sendFormatedMessage("&aSuccess!")
            }
            "on" -> {
                if(whiteListController.isEnabled()){
                    sender.sendFormatedMessage("&cWhitelist is enabled!")
                    return true
                }
                whiteListController.enableWhiteList()
            }
            "off" -> {
                if(!whiteListController.isEnabled()){
                    sender.sendFormatedMessage("&cWhitelist is disabled!")
                    return true
                }
                whiteListController.disableWhiteList()
            }
            else -> {
                sender.sendFormatedMessage("&cInvalid usage!")
            }
        }
        return true
    }
}