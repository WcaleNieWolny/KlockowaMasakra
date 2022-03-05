package pl.wolny.klockowamaskaradisguise.master.controller

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.PacketContainer
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import pl.wolny.klockowamaskaradisguise.api.FakePlayerController
import pl.wolny.klockowamaskaradisguise.api.PossessedEntity
import java.util.*

class FakePlayerController: Listener, FakePlayerController{

    private val disguisedPlayers: MutableMap<UUID, PossessedEntity> = mutableMapOf()

    override fun disguisePlayer(player: Player, possessedEntity: PossessedEntity) {
        possessedEntity.setup()
        disguisedPlayers[player.uniqueId] = possessedEntity

        removeEntity(possessedEntity.mob().entityId, player)
        player.teleport(possessedEntity.currentLocation())
    }

    override fun revealPlayer(player: Player) {
        val possessedEntity = disguisedPlayers[player.uniqueId] ?: throw IllegalStateException("Player ${player.name} is not disguised!")
        possessedEntity.reveal()
        disguisedPlayers.remove(player.uniqueId)
    }

    override fun isDisguised(player: Player) = disguisedPlayers.containsKey(player.uniqueId)

    @EventHandler(ignoreCancelled = true)
    fun playerMoveEvent(event: PlayerMoveEvent){
        val disguisedPlayer = disguisedPlayers[event.player.uniqueId] ?: return
        if(disguisedPlayer.currentLocation() != event.to){
            disguisedPlayer.move(event.to)
        }
    }

    fun removeEntity(id: Int, player: Player){
        val packet = PacketContainer(PacketType.Play.Server.ENTITY_DESTROY)
        packet.intLists.write(0, listOf(id))
        ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet)
    }
}