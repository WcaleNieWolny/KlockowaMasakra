package pl.wolny.klockowamasakralifes.controller

import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin
import pl.wolny.klockowamasakralifes.ban.PlayerBan
import pl.wolny.klockowamasakralifes.formatMessage
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

class UserController(
    private val plugin: JavaPlugin,
    private val deathMessage: String,
    private val liveOnStart: Int,
    private val banTime: Int

) : Listener {

    private val livesMap = Collections.synchronizedMap(mutableMapOf<UUID, Int>())
    val banMap: MutableMap<String, PlayerBan> = mutableMapOf()

    fun setLives(player: Player, int: Int) {
        player.persistentDataContainer.set(
            NamespacedKey(plugin, "WOLNY_LIVES"),
            PersistentDataType.INTEGER,
            int
        )
        livesMap[player.uniqueId] = int
    }

    fun getLives(player: Player): Int {
        val cacheLives = livesMap[player.uniqueId]
        if (cacheLives != null) {
            return cacheLives
        }
        val lives: Int?
        if(!Bukkit.isPrimaryThread()){
            val completablePersistentFuture = CompletableFuture<Int?>()
            Bukkit.getScheduler().runTask(plugin, Runnable {
                completablePersistentFuture.complete(
                    player.persistentDataContainer.get(
                        NamespacedKey(plugin, "WOLNY_LIVES"),
                        PersistentDataType.INTEGER
                    )
                )
            })
            lives = completablePersistentFuture.get(5, TimeUnit.SECONDS)
        }else{
            lives = player.persistentDataContainer.get(
                NamespacedKey(plugin, "WOLNY_LIVES"),
                PersistentDataType.INTEGER
            )
        }

        if (lives != null) {
            livesMap[player.uniqueId] = lives
            return lives
        }
        setLives(player, liveOnStart)
        return liveOnStart
    }

    fun tempBanPlayer(player: Player, time: Int, timeUnit: Int) {
        val calendar = Calendar.getInstance()
        calendar.add(timeUnit, time)
        val banTime = calendar.time.time

        banMap[player.name] = PlayerBan(player.uniqueId, banTime)
    }

    @EventHandler(ignoreCancelled = true)
    fun onDeathEvent(event: PlayerDeathEvent) {
        val player = event.player
        val playerLives = getLives(player) - 1
        if (playerLives == -1) {
            tempBanPlayer(player, banTime, Calendar.MINUTE)
            return
        }
        setLives(event.player, playerLives)
    }

    @EventHandler
    fun onPlayerRespawn(event: PlayerRespawnEvent) {
        val player = event.player
        val playerBan = banMap[player.name]
        if (playerBan != null) {
            val minutes = playerBan.time.minus(System.currentTimeMillis()).div((60 * 1000)) % 60
            player.kick(getKickComponent(minutes))
        }
    }

    @EventHandler
    fun onDeathJoin(event: AsyncPlayerPreLoginEvent) {
        val name = event.playerProfile.name ?: return
        val playerBan = banMap[name] ?: return
        val minutes = (System.currentTimeMillis() - playerBan.time) / (60 * 1000) % 60
        if (System.currentTimeMillis() >= playerBan.time) {
            banMap.remove(name)
            return
        }
        event.disallow(
            AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST,
            getKickComponent(minutes)
        )
    }

    private fun getKickComponent(minutes: Long) = formatMessage(deathMessage.replace("#MINUTES#", minutes.toString()))
}