package pl.wolny.kwadratowamasakratablist

import kr.entree.spigradle.annotations.SpigotPlugin
import net.dzikoysk.cdn.KCdnFactory
import net.dzikoysk.cdn.loadAs
import net.dzikoysk.cdn.source.Source
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import pl.wolny.kwadratowamasakratablist.hook.VaultHook
import pl.wolny.kwadratowamasakratablist.model.configuration.TabListConfig
import pl.wolny.kwadratowamasakratablist.model.frame.DeathFrame
import pl.wolny.kwadratowamasakratablist.model.frame.EmptyFrame
import pl.wolny.kwadratowamasakratablist.model.frame.PlayerFrame
import pl.wolny.kwadratowamasakratablist.model.frame.PrePlayerFrame
import pl.wolny.kwadratowamasakratablist.model.user.TabListRepository
import pl.wolny.kwadratowamasakratablist.render.RenderController
import pl.wolny.kwadratowamasakratablist.render.UpdateTask
import java.io.File
import java.util.*
import java.util.logging.Level


@SpigotPlugin
class KwadratowaMasakraTabList: JavaPlugin(), Listener {

    private val tabListConfig: TabListConfig = createConfig()
    private val renderController: RenderController = RenderController(tabListConfig.ping)
    private val vaultHook = VaultHook()
    private val repository = TabListRepository(dataFolder, vaultHook)
    private val playerFrame = PlayerFrame(vaultHook, repository, this)
    private val deathFrame = DeathFrame(repository)
    private val updateTask = UpdateTask(renderController)


    override fun onEnable() {
        // Plugin startup logic
        val pluginManager = Bukkit.getPluginManager()
        pluginManager.registerEvents(this, this)
        pluginManager.registerEvents(repository, this)
        renderController.registerFrame(PrePlayerFrame())
        renderController.registerFrame(playerFrame)
        renderController.registerFrame(EmptyFrame())
        renderController.registerFrame(deathFrame)
        updateTask.runTaskTimerAsynchronously(this, 20, 20)
        vaultHook.setup()
        repository.setUp()

    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    fun createConfig(): TabListConfig{
        val cdn = KCdnFactory.createYamlLike()
        val configFile = File(this.dataFolder, "config.yml")
        val configSource = Source.of(configFile)
        val configResult = cdn.loadAs<TabListConfig>(configSource)
        if(configResult.isErr){
            logger.log(Level.SEVERE, "Can't create config file!")
            configResult.error.printStackTrace()
            pluginLoader.disablePlugin(this)
            return TabListConfig() //Can't return null
        }
        val config = configResult.get()
        cdn.render(config, configSource)
        return config
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent){
        val player = event.player
        Bukkit.getScheduler().runTaskAsynchronously(this, Runnable { renderController.render(player, false)  })

//        player.sendTabListEntity(PlayerInfoData(
//            WrappedGameProfile(UUID.randomUUID(), "ADAM"),
//            10,
//            EnumWrappers.NativeGameMode.ADVENTURE,
//            WrappedChatComponent.fromLegacyText("&cAdam")
//        ))
    }
}