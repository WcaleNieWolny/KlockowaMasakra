package pl.wolny.kwadratowamasakratablist.model.frame

import org.bukkit.entity.Player
import pl.wolny.kwadratowamasakratablist.model.player.EmptyTabListPlayer
import pl.wolny.kwadratowamasakratablist.model.player.TabListPlayer

class SimpleFrame: Frame {
    override fun render(player: Player): List<TabListPlayer> {
        return listOf(EmptyTabListPlayer(), EmptyTabListPlayer(), EmptyTabListPlayer())
    }
}