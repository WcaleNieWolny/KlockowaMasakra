package pl.wolny.klockowamasakralifes.command

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import pl.wolny.klockowamasakralifes.controller.UserController
import pl.wolny.klockowamasakralifes.formatMessage

class PardonCommand(
    private val userController: UserController,
    private val canNotUseThisCommand: String,
    private val pardonCommand: String,
    private val pardonAllCommand: String,
    private val pardonNoPlayerFound: String,
    private val invalidUsage: String
): CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(!sender.hasPermission("klockowalifes.pardon")){
            sender.sendMessage(formatMessage(canNotUseThisCommand))
        }
        if(args.isEmpty()){
            sender.sendMessage(invalidUsage
                .replace("#USAGE#", "/livepardon player | /livepardon *"))
        }
        val argument = args[0]

        if(argument == "*"){
            userController.banMap.clear()
            sender.sendMessage(formatMessage(pardonAllCommand))
        }else if(userController.banMap.remove(argument) == null){
            //Can't find player in ban map
            sender.sendMessage(pardonNoPlayerFound)
        }else {
            //Unbanned player
            sender.sendMessage(formatMessage(pardonCommand
                .replace("#PLAYER#", argument)))
        }
        return true
    }
}