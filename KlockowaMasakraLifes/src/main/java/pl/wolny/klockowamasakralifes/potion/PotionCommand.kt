package pl.wolny.klockowamasakralifes.potion

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class PotionCommand(private val potionService: PotionService): CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val player = sender as Player
        player.inventory.addItem(potionService.createPotion())
        return true
    }
}