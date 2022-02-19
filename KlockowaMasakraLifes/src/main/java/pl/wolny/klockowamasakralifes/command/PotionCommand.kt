package pl.wolny.klockowamasakralifes.command

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import pl.wolny.klockowamasakralifes.formatMessage
import pl.wolny.klockowamasakralifes.potion.PotionService

class PotionCommand(
    private val potionService: PotionService,
    private val canNotGetPotion: String,
    private val canNotUseThisCommand: String,
    private val potionRecived: String
) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(formatMessage(canNotUseThisCommand))
            return true
        }
        if (!sender.hasPermission("klockowalifes.potion")) {
            sender.sendMessage(formatMessage(canNotGetPotion))
            return true
        }
        sender.inventory.addItem(potionService.createPotion())
        sender.sendMessage(formatMessage(potionRecived))
        return true
    }
}