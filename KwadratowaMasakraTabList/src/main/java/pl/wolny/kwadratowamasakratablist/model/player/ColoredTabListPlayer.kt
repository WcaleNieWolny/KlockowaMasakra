package pl.wolny.kwadratowamasakratablist.model.player

import com.comphenix.protocol.wrappers.WrappedChatComponent
import com.comphenix.protocol.wrappers.WrappedSignedProperty
import net.kyori.adventure.bossbar.BossBar
import net.md_5.bungee.api.ChatColor
import org.bukkit.Color

class ColoredTabListPlayer(private val name: String): TabListPlayer {
    override fun skin(): WrappedSignedProperty {
        return WrappedSignedProperty("textures", EmptyTabListPlayer.skinValue, EmptyTabListPlayer.skinSignature)
    }

    override fun name(): WrappedChatComponent {
        return WrappedChatComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', name))
    }
}