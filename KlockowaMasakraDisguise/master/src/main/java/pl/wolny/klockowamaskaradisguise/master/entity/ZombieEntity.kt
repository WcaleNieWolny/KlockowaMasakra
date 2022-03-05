package pl.wolny.klockowamaskaradisguise.master.entity

import org.bukkit.entity.EntityType
import org.bukkit.entity.Zombie
import org.bukkit.inventory.ItemStack

class ZombieEntity(private val zombie: Zombie): AbstractEntity(zombie) {

    override fun type(): EntityType {
        return EntityType.ZOMBIE
    }

    override fun customItems(): List<ItemStack> {
        TODO("Not yet implemented")
    }
}