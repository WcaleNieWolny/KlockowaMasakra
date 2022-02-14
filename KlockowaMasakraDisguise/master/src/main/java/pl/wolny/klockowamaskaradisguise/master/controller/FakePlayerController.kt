package pl.wolny.klockowamaskaradisguise.master.controller

import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import pl.wolny.klockowamaskaradisguise.api.FakePlayerController
import pl.wolny.klockowamaskaradisguise.api.PossessedEntity
import java.util.*
import java.util.function.Supplier
import kotlin.collections.HashMap

class FakePlayerController: Listener, FakePlayerController{

    private val disguisedPlayers: MutableMap<UUID, PossessedEntity> = mutableMapOf()

    override fun disguisePlayer(player: Player, possessedEntity: PossessedEntity) {
        possessedEntity.setup()
        disguisedPlayers[player.uniqueId] = possessedEntity

        player.teleport(possessedEntity.currentLocation())
    }

    fun playerMoveEvent(event: PlayerMoveEvent){
        val disguisedPlayer = disguisedPlayers[event.player.uniqueId] ?: return
        disguisedPlayer.move(event.to)
    }

    fun removeEntity(id: Int, player: Player){

    }
}