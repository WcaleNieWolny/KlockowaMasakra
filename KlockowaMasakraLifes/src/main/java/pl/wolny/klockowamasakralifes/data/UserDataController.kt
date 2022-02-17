package pl.wolny.klockowamasakralifes.data

import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap

class UserDataController(private val plugin: JavaPlugin) {

    private val livesMap = Collections.synchronizedMap(mutableMapOf<UUID, Int>())

    fun setLives(player: Player, int: Int){
        player.persistentDataContainer.set(
            NamespacedKey(plugin, "WOLNY_LIVES"),
            PersistentDataType.INTEGER,
            int
        )
        livesMap[player.uniqueId] = int
    }

    fun getLives(player: Player): Int{
        val cacheLives = livesMap[player.uniqueId]
        if(cacheLives != null){
            return cacheLives
        }
        val completablePersistentFuture = CompletableFuture<Int?>()
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin) {
            completablePersistentFuture.complete(
                player.persistentDataContainer.get(
                    NamespacedKey(plugin, "WOLNY_LIVES"),
                    PersistentDataType.INTEGER
                )
            )
        }
        val lives = completablePersistentFuture.get()
        if(lives != null){
            livesMap[player.uniqueId] = lives
            return lives
        }
        setLives(player, 3)
        return 3
    }

}