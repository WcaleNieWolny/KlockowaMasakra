package pl.wolny.klockowamaskaradisguise.master.entity

import org.bukkit.Location
import org.bukkit.entity.Entity
import pl.wolny.klockowamaskaradisguise.api.PossessedEntity

abstract class AbstractEntity(private val entity: Entity): PossessedEntity {
    override fun mob() = entity
    override fun currentLocation() = entity.location
}