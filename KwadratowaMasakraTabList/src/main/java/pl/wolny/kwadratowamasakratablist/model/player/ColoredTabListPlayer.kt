package pl.wolny.kwadratowamasakratablist.model.player

import com.comphenix.protocol.wrappers.WrappedChatComponent
import com.comphenix.protocol.wrappers.WrappedSignedProperty
import net.kyori.adventure.bossbar.BossBar
import net.md_5.bungee.api.ChatColor
import org.bukkit.Color

class ColoredTabListPlayer(name: String): AbstractColoredTabListPlayer(name) {
    override fun skin(): WrappedSignedProperty {
        return WrappedSignedProperty("textures", AbstractEmptyTabListPlayer.skinValue, AbstractEmptyTabListPlayer.skinSignature)
    }
}