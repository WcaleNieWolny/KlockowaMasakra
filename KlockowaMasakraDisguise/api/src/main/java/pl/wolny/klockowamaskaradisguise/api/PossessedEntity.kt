package pl.wolny.klockowamaskaradisguise.api

import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.inventory.ItemStack

interface PossessedEntity {

    fun move(location: Location)
    fun currentLocation(): Location
    fun mob(): Entity
    fun type(): EntityType
    fun customItems(): List<ItemStack>
    fun setup()
}