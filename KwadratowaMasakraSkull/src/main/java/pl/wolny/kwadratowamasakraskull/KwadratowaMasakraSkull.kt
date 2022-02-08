package pl.wolny.kwadratowamasakraskull

import kr.entree.spigradle.annotations.SpigotPlugin
import net.dzikoysk.cdn.KCdnFactory
import net.dzikoysk.cdn.loadAs
import net.dzikoysk.cdn.source.Source
import org.bukkit.Bukkit
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.ServicePriority
import org.bukkit.plugin.java.JavaPlugin
import pl.wolny.kwadratowamasakraskull.api.SkullApi
import pl.wolny.kwadratowamasakraskull.api.SkullApiImpl
import pl.wolny.kwadratowamasakraskull.configuatuion.PluginConfig
import pl.wolny.kwadratowamasakraskull.controller.SkullPlayerController
import pl.wolny.kwadratowamasakraskull.extenstion.setData
import pl.wolny.kwadratowamasakraskull.hook.VaultHook
import pl.wolny.kwadratowamasakraskull.model.SkullPlayer
import java.io.File
import java.util.*
import java.util.logging.Level

@SpigotPlugin
class KwadratowaMasakraSkull: JavaPlugin() {

    private val config = createConfig()
    private val vaultHook = VaultHook(this)
    private val skullPlayerController = SkullPlayerController(config, vaultHook)

    override fun onEnable() {
        //startup logic
        Bukkit.getPluginManager().registerEvents(skullPlayerController, this)
        Bukkit.getServicesManager().register(SkullApi::class.java, SkullApiImpl(skullPlayerController), this, ServicePriority.Normal)
        vaultHook.setup()
    }

    override fun onDisable() {
        skullPlayerController.skullPlayers.forEach{
            Bukkit.getOfflinePlayer(it.key).setData("Skull.T", PersistentDataType.LONG, it.value.skullTime)
        }
        //shutdown logic
    }

    fun createConfig(): PluginConfig{
        val cdn = KCdnFactory.createYamlLike()
        val configFile = File(this.dataFolder, "config.yml")
        val configSource = Source.of(configFile)
        val configResult = cdn.loadAs<PluginConfig>(configSource)
        if(configResult.isErr){
            logger.log(Level.SEVERE, "Can't create config file!")
            configResult.error.printStackTrace()
            pluginLoader.disablePlugin(this)
            return PluginConfig() //Can't return null
        }
        val config = configResult.get()
        cdn.render(config, configSource)
        return config
    }

}