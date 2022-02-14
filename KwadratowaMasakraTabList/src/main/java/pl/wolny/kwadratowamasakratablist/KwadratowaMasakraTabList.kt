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
import pl.wolny.kwadratowamasakratablist.hook.FunnyGuildsHook
import pl.wolny.kwadratowamasakratablist.hook.SkullHook
import pl.wolny.kwadratowamasakratablist.hook.VaultHook
import pl.wolny.kwadratowamasakratablist.model.configuration.TabListConfig
import pl.wolny.kwadratowamasakratablist.model.frame.*
import pl.wolny.kwadratowamasakratablist.model.user.TabListRepository
import pl.wolny.kwadratowamasakratablist.render.FooterHeaderRenderController
import pl.wolny.kwadratowamasakratablist.render.RenderController
import pl.wolny.kwadratowamasakratablist.render.UpdateTask
import java.io.File
import java.util.*
import java.util.logging.Level


@SpigotPlugin
class KwadratowaMasakraTabList: JavaPlugin(), Listener {

    private val funnyGuildsHook = FunnyGuildsHook()
    private val tabListConfig: TabListConfig = createConfig()
    private val vaultHook = VaultHook()
    private val skullHook = SkullHook()

    private val renderController: RenderController = RenderController(tabListConfig.ping, FooterHeaderRenderController(tabListConfig.serverBrand, tabListConfig.tabListFooter, vaultHook))
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

        setupRuntime()

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

    private fun setupRuntime(){
        val funnyGuildsPlugin = Bukkit.getServer().pluginManager.getPlugin("FunnyGuilds")
        if(funnyGuildsPlugin != null){
            return
        }

        
    }

}