package pl.wolny.kwadratowamasakraskull.controller

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import pl.wolny.kwadratowamasakraskull.configuatuion.PluginConfig
import pl.wolny.kwadratowamasakraskull.extenstion.haveSkull
import pl.wolny.kwadratowamasakraskull.extenstion.setSkull
import pl.wolny.kwadratowamasakraskull.hook.VaultHook
import pl.wolny.kwadratowamasakraskull.model.SkullPlayer


class SkullPlayerController(private val pluginConfig: PluginConfig, private val vaultHook: VaultHook): Listener{

    @EventHandler
    fun onPlayerKill(event: PlayerDeathEvent) {
        val player = event.player
        val killer = player.killer

        if(killer !is Player){
            return
        }
        if(!player.haveSkull()){
            val response = vaultHook.economy!!.withdrawPlayer(killer, pluginConfig.skullPenalty.toDouble())
            if(!response.transactionSuccess()){
                val console = Bukkit.getServer().consoleSender
                Bukkit.dispatchCommand(console, pluginConfig.minimumBalanceCommand)
                return
            }

            killer.setSkull(SkullPlayer(true, 3_600_000L * pluginConfig.skullTime))
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

            player.setSkull(SkullPlayer(false, 0))
            Bukkit.broadcast(LegacyComponentSerializer.legacyAmpersand().deserialize(
                pluginConfig.guiltyMessage
                    .replace("#USER-1#", killer.name)
                    .replace("#USER-2#", player.name)
                    .replace("#REWARD#", pluginConfig.skullReward.toString())
            ))
        }
    }
}