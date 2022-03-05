package pl.wolny.klockowamaskaradisguise.master.entity

import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import pl.wolny.klockowamaskaradisguise.api.PossessedEntity
import pl.wolny.klockowamaskaradisguise.master.utils.MinecraftUtils

abstract class AbstractEntity(private val entity: LivingEntity): PossessedEntity {
    override fun mob() = entity

    override fun currentLocation() = entity.location

    override fun move(location: Location) = MinecraftUtils.moveEntity(entity, location)

    override fun setup() {
        entity.isCollidable = false
        entity.setAI(false)
    }

    override fun reveal() {
        entity.setAI(true)
    }
}