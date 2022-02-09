package pl.wolny.klockowamaskaradisguise.api

import org.bukkit.entity.EntityType
import org.bukkit.entity.Player

interface FakePlayerController {
    fun<T: PossessedEntity> registerType(entityType: EntityType, t: T)
    fun disguisePlayer(player: Player, possessedEntity: PossessedEntity)
}