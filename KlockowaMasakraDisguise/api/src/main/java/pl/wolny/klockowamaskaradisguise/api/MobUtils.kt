package pl.wolny.klockowamaskaradisguise.api

import org.bukkit.Location
import org.bukkit.entity.Entity

interface MobUtils {
    fun moveEntity(entity: Entity, location: Location)
}