package pl.wolny.klockowamasakralifes.command

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import pl.wolny.klockowamasakralifes.controller.UserController
import pl.wolny.klockowamasakralifes.formatMessage

class AddLivesCommand(
    private val userController: UserController,
    private val canNotUseThisCommand: String,
    private val invalidUsage: String,
    private val giveLivesCommand: String,
    private val playerNotOnline: String
): CommandExecutor {

    private val usage = formatMessage(invalidUsage
        .replace("#USAGE#", "/givelives player ammount"))

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if(!sender.hasPermission("klockowalifes.givelive")){
            sender.sendMessage(formatMessage(canNotUseThisCommand))
            return true
        }

        if(args.size != 2){
            sender.sendMessage(usage)
            return true
        }

        val player = Bukkit.getPlayer(args[0])
        if(player == null){
            sender.sendMessage(formatMessage(playerNotOnline))
            return true
        }

        val amount = args[1].toIntOrNull()
        if(amount == null){
            sender.sendMessage(usage)
            return true
        }
        if(0 >= amount){
            sender.sendMessage(usage)
            return true
        }

        userController.setLives(player, userController.getLives(player)+amount)
        sender.sendMessage(formatMessage(giveLivesCommand
            .replace("#PLAYER#", player.name)
            .replace("#AMMOUNT#", amount.toString())))
        return true
    }
}