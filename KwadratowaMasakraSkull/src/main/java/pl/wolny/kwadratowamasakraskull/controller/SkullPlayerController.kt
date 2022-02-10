package pl.wolny.kwadratowamasakraskull.controller

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.persistence.PersistentDataType
import pl.wolny.kwadratowamasakraskull.configuatuion.PluginConfig
import pl.wolny.kwadratowamasakraskull.extenstion.getData
import pl.wolny.kwadratowamasakraskull.extenstion.removeData
import pl.wolny.kwadratowamasakraskull.extenstion.setData
import pl.wolny.kwadratowamasakraskull.hook.VaultHook
import pl.wolny.kwadratowamasakraskull.model.SkullPlayer
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.HashMap


class SkullPlayerController(private val pluginConfig: PluginConfig, private val vaultHook: VaultHook): Listener{

    val skullPlayers: ConcurrentHashMap<UUID, SkullPlayer> = ConcurrentHashMap<UUID, SkullPlayer>()

    @EventHandler(ignoreCancelled = true)
    fun onPlayerKill(event: PlayerDeathEvent) {
        val player = event.player
        val killer = player.killer

        if(killer !is Player){
            return
        }
        if(!haveSkull(player.uniqueId)){
            val response = vaultHook.economy!!.withdrawPlayer(killer, pluginConfig.skullPenalty.toDouble())
            if(!response.transactionSuccess()){
                val console = Bukkit.getServer().consoleSender
                Bukkit.dispatchCommand(console, pluginConfig.minimumBalanceCommand)
                return
            }

            setSkull(killer, SkullPlayer(3_600_000L * pluginConfig.skullTime))

            Bukkit.broadcast(LegacyComponentSerializer.legacyAmpersand().deserialize(
                pluginConfig.nonGuiltyMessage
                    .replace("#USER-1#", killer.name)
                    .replace("#USER-2#", player.name)
                    .replace("#PENALTY#", pluginConfig.skullPenalty.toString())
            ))
        }else{
            val result = vaultHook.economy!!.depositPlayer(killer, pluginConfig.skullReward.toDouble())
            if(!result.transactionSuccess()){
                return
            }

            removeSkull(player.uniqueId)

            Bukkit.broadcast(LegacyComponentSerializer.legacyAmpersand().deserialize(
                pluginConfig.guiltyMessage
                    .replace("#USER-1#", killer.name)
                    .replace("#USER-2#", player.name)
                    .replace("#REWARD#", pluginConfig.skullReward.toString())
            ))
        }
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent){
        val player = event.player
        val skullTime = player.getData("Skull.T", PersistentDataType.LONG) ?: return
        val skullPlayer = SkullPlayer(skullTime)
        player.removeData("Skull.T")
        skullPlayers[player.uniqueId] = skullPlayer
    }

    private fun setSkull(player: Player, skullPlayer: SkullPlayer){
        skullPlayers[player.uniqueId] = skullPlayer
    }

    private fun removeSkull(uuid: UUID) = skullPlayers.remove(uuid)

    fun haveSkull(uuid: UUID): Boolean{
        return skullPlayers.containsKey(uuid)
    }

}