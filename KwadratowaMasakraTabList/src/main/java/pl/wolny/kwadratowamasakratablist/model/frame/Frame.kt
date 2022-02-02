package pl.wolny.kwadratowamasakratablist.model.frame

import org.bukkit.entity.Player
import pl.wolny.kwadratowamasakratablist.model.player.TabListPlayer

interface Frame {
    fun render(player: Player): List<TabListPlayer>
}