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
import java.util.*

@SpigotPlugin
class KwadratowaMasakraTabList: JavaPlugin(), Listener {

    override fun onEnable() {
        // Plugin startup logic
        val pluginManager = Bukkit.getPluginManager()
        pluginManager.registerEvents(this, this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent){
        val player = event.player
        player.sendTabListEntity(PlayerInfoData(
            WrappedGameProfile(UUID.randomUUID(), "ADAM"),
            10,
            EnumWrappers.NativeGameMode.ADVENTURE,
            WrappedChatComponent.fromLegacyText("&cAdam")
        ))
    }
}