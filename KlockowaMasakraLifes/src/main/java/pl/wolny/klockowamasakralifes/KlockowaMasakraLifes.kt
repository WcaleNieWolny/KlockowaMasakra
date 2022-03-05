package pl.wolny.klockowamasakralifes

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kr.entree.spigradle.annotations.SpigotPlugin
import net.dzikoysk.cdn.KCdnFactory
import net.dzikoysk.cdn.loadAs
import net.dzikoysk.cdn.source.Source
import org.bukkit.Bukkit
import org.bukkit.plugin.ServicePriority
import org.bukkit.plugin.java.JavaPlugin
import pl.wolny.klockowamasakralifes.api.LivesAPI
import pl.wolny.klockowamasakralifes.api.LivesApiImpl
import pl.wolny.klockowamasakralifes.ban.PlayerBan
import pl.wolny.klockowamasakralifes.command.AddLivesCommand
import pl.wolny.klockowamasakralifes.command.LivesCommand
import pl.wolny.klockowamasakralifes.command.PardonCommand
import pl.wolny.klockowamasakralifes.configuration.PluginConfiguration
import pl.wolny.klockowamasakralifes.controller.UserController
import pl.wolny.klockowamasakralifes.command.PotionCommand
import pl.wolny.klockowamasakralifes.potion.PotionService
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.util.logging.Level

@SpigotPlugin
class KlockowaMasakraLifes : JavaPlugin() {

    private val configuration = createConfig()
    private val userController =
        UserController(this, configuration.deathKickMessage, configuration.livesOnStart, configuration.banTime)
    private val livesApiImpl = LivesApiImpl(
        userController,
        configuration.hearthSymbol,
        configuration.hearthColour,
        configuration.livesOverflow,
        configuration.noLivesMessage
    )
    private val potionService = PotionService(
        userController,
        this,
        configuration.red,
        configuration.green,
        configuration.blue,
        configuration.potionName
    )
    private val potionCommand = PotionCommand(
        potionService,
        configuration.canNotGetPotion,
        configuration.canNotUseThisCommand,
        configuration.potionRecived
    )
    private val livesCommand = LivesCommand(
        userController,
        configuration.canNotUseThisCommand,
        configuration.livesCommand,
        configuration.hearthSymbol,
        configuration.hearthColourMiniMessage
    )
    private val pardonCommand = PardonCommand(
        userController,
        configuration.canNotUseThisCommand,
        configuration.pardonCommand,
        configuration.pardonAllCommand,
        configuration.pardonNoPlayerFound,
        configuration.invalidUsage
    )
    private val addLivesCommand = AddLivesCommand(
        userController,
        configuration.canNotUseThisCommand,
        configuration.invalidUsage,
        configuration.giveLivesCommand,
        configuration.playerNotOnline
    )

    override fun onEnable() {
        Bukkit.getServicesManager().register(LivesAPI::class.java, livesApiImpl, this, ServicePriority.Normal)
        Bukkit.getPluginManager().registerEvents(userController, this)
        Bukkit.getPluginManager().registerEvents(potionService, this)
        getCommand("livepotion")?.setExecutor(potionCommand)
        getCommand("lives")?.setExecutor(livesCommand)
        getCommand("livepardon")?.setExecutor(pardonCommand)
        getCommand("givelives")?.setExecutor(addLivesCommand)
        loadBans()
    }

    override fun onDisable() {
        for (entry in userController.banMap) {
            if (System.currentTimeMillis() >= entry.value.time) {
                return
            }
            try {
                val folder = File(dataFolder, "ban-data")
                if (!folder.exists()) {
                    folder.mkdirs()
                }
                val file = File(folder, "${entry.key}.json")
                if (!file.exists()) {
                    file.createNewFile()
                }
                val writer = BufferedWriter(FileWriter(file))
                writer.append(Json.encodeToString(entry.value))
                writer.close()
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
        // Plugin shutdown logic
    }

    fun loadBans() {
        val folder = File(dataFolder, "ban-data")
        val files = folder.listFiles() ?: return
        var banLoaded = 0
        for (file in files) {
            val reader = file.bufferedReader()
            userController.banMap[file.nameWithoutExtension] =
                Json.decodeFromString<PlayerBan>(reader.readLine())
            reader.close()
            banLoaded++
        }
        logger.info("Loaded $banLoaded player bans!")
        purgeDirectory(folder)
    }

    fun createConfig(): PluginConfiguration {
        val cdn = KCdnFactory.createYamlLike()
        val configFile = File(this.dataFolder, "config.yml")
        val configSource = Source.of(configFile)
        val configResult = cdn.loadAs<PluginConfiguration>(configSource)
        if (configResult.isErr) {
            logger.log(Level.SEVERE, "Can't create config file!")
            configResult.error.printStackTrace()
            pluginLoader.disablePlugin(this)
            return PluginConfiguration() //Can't return null
        }
        val config = configResult.get()
        cdn.render(config, configSource)
        return config
    }

    fun purgeDirectory(dir: File) {
        for (file in dir.listFiles() ?: return) {
            if (file.isDirectory) purgeDirectory(file)
            file.delete()
        }
    }
}