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
import pl.wolny.kwadratowamasakratablist.model.configuration.TabListConfig
import pl.wolny.kwadratowamasakratablist.model.frame.SimpleFrame
import pl.wolny.kwadratowamasakratablist.render.RenderController
import java.io.File
import java.util.*
import java.util.logging.Level


@SpigotPlugin
class KwadratowaMasakraTabList: JavaPlugin(), Listener {

    private val tabListConfig: TabListConfig = createConfig()!!
    private val renderController = RenderController(tabListConfig.ping)
    private val simpleFrame = SimpleFrame()


    override fun onEnable() {
        // Plugin startup logic
        val pluginManager = Bukkit.getPluginManager()
        pluginManager.registerEvents(this, this)
        renderController.registerFrame(simpleFrame)

    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    fun createConfig(): TabListConfig?{
        val cdn = KCdnFactory.createYamlLike()
        val configFile = File(this.dataFolder, "config.yml")
        val configSource = Source.of(file)
        val configResult = cdn.loadAs<TabListConfig>(configSource)
        if(configResult.isErr){
            logger.log(Level.SEVERE, "Can't create config file!")
            configResult.error.printStackTrace()
            pluginLoader.disablePlugin(this)
            return null
        }
        cdn.render(config, configSource)
        return configResult.get()
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent){
        val player = event.player
        renderController.render(player)
//        player.sendTabListEntity(PlayerInfoData(
//            WrappedGameProfile(UUID.randomUUID(), "ADAM"),
//            10,
//            EnumWrappers.NativeGameMode.ADVENTURE,
//            WrappedChatComponent.fromLegacyText("&cAdam")
//        ))
    }
}