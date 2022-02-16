package pl.wolny.klockowamasakratablist.model.player

import com.comphenix.protocol.wrappers.WrappedChatComponent
import net.md_5.bungee.api.ChatColor

abstract class AbstractColoredTabListPlayer(private val name: String): TabListPlayer {
    override fun name(): WrappedChatComponent {
        return WrappedChatComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', name))
    }
}