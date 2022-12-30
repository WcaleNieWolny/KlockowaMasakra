package pl.wolny.klockowamasakratablist

import eu.okaeri.configs.ConfigManager
import eu.okaeri.configs.OkaeriConfig
import eu.okaeri.configs.OkaeriConfigInitializer
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer
import kr.entree.spigradle.annotations.SpigotPlugin
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

    private var renderController: RenderController? = null;

    override fun onEnable() {
        // Plugin startup logic

        val funnyGuildsHook = FunnyGuildsHook()
        val tabListConfig: TabListConfig? = createConfig()
        if (tabListConfig == null){
            this.pluginLoader.disablePlugin(this);
            return;
        }

        val vaultHook = VaultHook()
        val skullHook = SkullHook()
        val livesHook = LivesHook()

        this.renderController = RenderController(tabListConfig.ping, FooterHeaderRenderController(tabListConfig.serverBrand, tabListConfig.tabListFooter, vaultHook, livesHook))
        val repository = TabListRepository(dataFolder, vaultHook)
        val playerFrame = PlayerFrame(vaultHook, repository, this, skullHook, funnyGuildsHook)
        val deathFrame = DeathFrame(repository)
        val updateTask = UpdateTask(this.renderController!!)

        val pluginManager = Bukkit.getPluginManager()
        pluginManager.registerEvents(this, this)
        pluginManager.registerEvents(repository, this)

        vaultHook.setup()
        skullHook.setup()
        funnyGuildsHook.setup()
        repository.setUp()
        livesHook.setup()

        this.renderController!!.registerFrame(PrePlayerFrame())
        this.renderController!!.registerFrame(playerFrame)

        if(funnyGuildsHook.isAvailable()){
            this.renderController!!.registerFrame(GuildsFrame(funnyGuildsHook))
        }else{
            this.renderController!!.registerFrame(EmptyFrame())
        }

        this.renderController!!.registerFrame(deathFrame)

        updateTask.runTaskTimerAsynchronously(this, 20, 20)

    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    fun createConfig(): TabListConfig?{

        try {
            return ConfigManager.create(TabListConfig::class.java) { it: OkaeriConfig ->
                it.withConfigurer(YamlBukkitConfigurer())
                it.withBindFile(File(dataFolder, "settings.yml"))
                it.saveDefaults()
                it.load(true)
            }
        } catch (exception: Exception) {
            logger.log(Level.SEVERE, "Error loading config.yml", exception)
            return null
        }
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent){
        val player = event.player
        Bukkit.getScheduler().runTaskAsynchronously(this, Runnable { renderController!!.render(player, false)  })

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