package pl.wolny.klockowamasakratablist.model.user

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.bukkit.Bukkit
import org.bukkit.Statistic
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import pl.wolny.klockowamasakratablist.hook.VaultHook
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.util.*

class TabListRepository(private val dataFolder: File, private val vaultHook: VaultHook): Listener {

    val tabListUsers: MutableList<TabListUser> = arrayListOf()

    fun setUp() {
        val folder = File(dataFolder, "data")
        if (!folder.exists()) {
            folder.mkdirs()
        }
        val files = folder.listFiles() ?: return
        files.forEach {
            val userString = it.bufferedReader().readLine()
            try {
                val tabListUser: TabListUser = Json { ignoreUnknownKeys = true }.decodeFromString(TabListUser.serializer(), userString)
                tabListUser.deaths = Bukkit.getOfflinePlayer(UUID.fromString(it.nameWithoutExtension)).getStatistic(Statistic.DEATHS)
                tabListUser.uuid = UUID.fromString(it.nameWithoutExtension)
                tabListUsers.add(tabListUser)
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }

    private fun createTabListUser(player: Player): TabListUser = if (vaultHook.isAvailable()) TabListUser(
        player.name,
        vaultHook.provider()?.getBalance(player) ?: 0.0, player.getStatistic(Statistic.DEATHS), player.uniqueId
    ) else TabListUser(player.name, 0.0, player.getStatistic(Statistic.DEATHS), player.uniqueId)

    @EventHandler
    fun onDeathEvent(event: PlayerDeathEvent) {
        val player = event.player
        val tabListUser = tabListUsers.filter { it.name == player.name }[0]
        tabListUser.deaths++
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        val provider = vaultHook.provider() ?: return
        val player = event.player
        val playerFile = File(File(dataFolder, "data"), "${player.uniqueId}.json")
        if (!playerFile.exists()) {
            if (!playerFile.createNewFile()) {
                throw RuntimeException("Can not create user file! User: ${player.uniqueId}")
            }

            val tabListUser = tabListUsers.filter { it.uuid == player.uniqueId }[0]
            tabListUser.money = provider.getBalance(player)

            try {
                val writer = BufferedWriter(FileWriter(playerFile))
                writer.append(Json.encodeToString(tabListUser))
                writer.close()
            } catch (exception: Exception) {
                throw RuntimeException("Can not create user file! User: ${player.uniqueId}")
            }
        }
    }

    @EventHandler
    fun onJoinEvent(event: PlayerJoinEvent) {
        val player = event.player
        val playerFile = File(File(dataFolder, "data"), "${player.uniqueId}.json")
        if (!playerFile.exists()) {
            if (!playerFile.createNewFile()) {
                throw RuntimeException("Can not create user file! User: ${player.uniqueId}")
            }

            val tabListUser = createTabListUser(player)

            try {
                val writer = BufferedWriter(FileWriter(playerFile))
                writer.append(Json.encodeToString(tabListUser))
                writer.close()
            } catch (exception: Exception) {
                throw RuntimeException("Can not create user file! User: ${player.uniqueId}")
            }
            tabListUsers.add(tabListUser)
        }
    }
}