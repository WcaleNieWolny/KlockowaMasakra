package pl.wolny.klockowamasakratablist.model.frame

import org.bukkit.entity.Player
import pl.wolny.klockowamasakratablist.model.player.TabListPlayer

interface Frame {
    fun render(player: Player): List<TabListPlayer>
}