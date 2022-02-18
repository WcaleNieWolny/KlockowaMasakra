package pl.wolny.klockowamasakratablist

import kr.entree.spigradle.annotations.SpigotPlugin
import net.dzikoysk.cdn.KCdnFactory
import net.dzikoysk.cdn.loadAs
import net.dzikoysk.cdn.source.Source
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import pl.wolny.klockowamasakratablist.hook.FunnyGuildsHook
import pl.wolny.klockowamasakratablist.hook.LivesHook
import pl.wolny.klockowamasakratablist.hook.SkullHook
import pl.wolny.klockowamasakratablist.hook.VaultHook
import pl.wolny.klockowamasakratablist.model.configuration.TabListConfig
import pl.wolny.klockowamasakratablist.model.frame.*
import pl.wolny.klockowamasakratablist.model.user.TabListRepository
import pl.wolny.klockowamasakratablist.render.FooterHeaderRenderController
import pl.wolny.klockowamasakratablist.render.RenderController
import pl.wolny.klockowamasakratablist.render.UpdateTask
import java.io.File
import java.util.logging.Level


@SpigotPlugin
class KlockowaMasakraTabList: JavaPlugin(), Listener {

    private val funnyGuildsHook = FunnyGuildsHook()
    private val tabListConfig: TabListConfig = createConfig()
    private val vaultHook = VaultHook()
    private val skullHook = SkullHook()
    private val livesHook = LivesHook()

    private val renderController: RenderController = RenderController(tabListConfig.ping, FooterHeaderRenderController(tabListConfig.serverBrand, tabListConfig.tabListFooter, vaultHook, livesHook))
    private val repository = TabListRepository(dataFolder, vaultHook)
    private val playerFrame = PlayerFrame(vaultHook, repository, this, skullHook, funnyGuildsHook)
    private val deathFrame = DeathFrame(repository)
    private val updateTask = UpdateTask(renderController)

    override fun onEnable() {
        // Plugin startup logic
        val pluginManager = Bukkit.getPluginManager()
        pluginManager.registerEvents(this, this)
        pluginManager.registerEvents(repository, this)

        vaultHook.setup()
        skullHook.setup()
        funnyGuildsHook.setup()
        repository.setUp()
        livesHook.setup()

        renderController.registerFrame(PrePlayerFrame())
        renderController.registerFrame(playerFrame)

        if(funnyGuildsHook.isAvailable()){
            renderController.registerFrame(GuildsFrame(funnyGuildsHook))
        }else{
            renderController.registerFrame(EmptyFrame())
        }

        renderController.registerFrame(deathFrame)

        updateTask.runTaskTimerAsynchronously(this, 20, 20)

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

//    private fun setupRuntime(){
//        val funnyGuildsPlugin = Bukkit.getServer().pluginManager.getPlugin("FunnyGuilds")
//        if(funnyGuildsPlugin != null){
//            return
//        }
//    }

}