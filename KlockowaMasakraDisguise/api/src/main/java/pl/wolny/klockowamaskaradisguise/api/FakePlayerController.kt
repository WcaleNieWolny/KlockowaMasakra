package pl.wolny.klockowamaskaradisguise.api

import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import java.util.function.Supplier

interface FakePlayerController {
    fun disguisePlayer(player: Player, possessedEntity: PossessedEntity)
    fun revealPlayer(player: Player)
    fun isDisguised(player: Player): Boolean
}