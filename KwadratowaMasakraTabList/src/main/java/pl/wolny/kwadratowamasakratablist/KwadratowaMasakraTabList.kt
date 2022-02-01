package pl.wolny.kwadratowamasakratablist

import com.comphenix.protocol.wrappers.EnumWrappers
import com.comphenix.protocol.wrappers.PlayerInfoData
import com.comphenix.protocol.wrappers.WrappedChatComponent
import com.comphenix.protocol.wrappers.WrappedGameProfile
import kr.entree.spigradle.annotations.SpigotPlugin
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import pl.wolny.kwadratowamasakratablist.extension.sendTabListEntity
import pl.wolny.kwadratowamasakratablist.model.frame.SimpleFrame
import pl.wolny.kwadratowamasakratablist.render.RenderController
import java.util.*

@SpigotPlugin
class KwadratowaMasakraTabList: JavaPlugin(), Listener {

    val renderController = RenderController()
    val simpleFrame = SimpleFrame()

    override fun onEnable() {
        // Plugin startup logic
        val pluginManager = Bukkit.getPluginManager()
        pluginManager.registerEvents(this, this)
        renderController.registerFrame(simpleFrame)
    }

    override fun onDisable() {
        // Plugin shutdown logic
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