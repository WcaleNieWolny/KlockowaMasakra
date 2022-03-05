package pl.wolny.klockowamaskaradisguise.master

import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.entity.Zombie
import org.bukkit.util.BoundingBox
import org.bukkit.util.Vector
import pl.wolny.klockowamaskaradisguise.master.controller.FakePlayerController
import pl.wolny.klockowamaskaradisguise.master.entity.ZombieEntity

class TestCommand(val fakePlayerController: FakePlayerController): CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender !is Player){
            return true
        }
        val zombie = sender.world.getNearbyEntities(sender.location, 3.0, 3.0, 3.0).filter {
            it.type == EntityType.ZOMBIE
        }.map{
            it as Zombie
        }.firstOrNull() ?: return true
        val zombieEntity = ZombieEntity(zombie)
        fakePlayerController.disguisePlayer(sender, zombieEntity)
        return true
    }
}