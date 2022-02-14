package pl.wolny.klockowamaskaradisguise.master.entity

import org.bukkit.Location
import org.bukkit.entity.EntityType
import org.bukkit.entity.Zombie
import org.bukkit.inventory.ItemStack

class ZombieEntity(private val zombie: Zombie): AbstractEntity(zombie) {
    override fun move(location: Location) {
        zombie.pathfinder.moveTo(location)
        zombie.lookAt(location)
    }

    override fun type(): EntityType {
        return EntityType.ZOMBIE
    }

    override fun customItems(): List<ItemStack> {
        TODO("Not yet implemented")
    }

    override fun setup() {
        zombie.pathfinder.setCanFloat(true)
    }
}