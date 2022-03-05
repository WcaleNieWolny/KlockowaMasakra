package pl.wolny.klockowamasakralifes.command

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import pl.wolny.klockowamasakralifes.controller.UserController
import pl.wolny.klockowamasakralifes.formatMessage

class LivesCommand(
    private val userController: UserController,
    private val canNotUseThisCommand: String,
    private val livesCommand: String,
    private val hearthSymbol: String,
    private val hearthColourMiniMessage: String
): CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender !is Player){
            sender.sendMessage(formatMessage(canNotUseThisCommand))
            return true
        }
        sender.sendMessage(formatMessage(livesCommand
            .replace("#LIVES#", userController.getLives(sender).toString())
            .replace("#HEARTH_SYMBOL#", hearthSymbol)
            .replace("#HEARTH_COLOUR_MINIMESSAGE#", hearthColourMiniMessage)))
        return true
    }
}