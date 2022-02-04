package pl.wolny.kwadratowamasakratablist.model.frame

import org.bukkit.entity.Player
import pl.wolny.kwadratowamasakratablist.model.player.EmptyTabListPlayer
import pl.wolny.kwadratowamasakratablist.model.player.TabListPlayer

class EmptyFrame: Frame {
    override fun render(player: Player): List<TabListPlayer> {
        val tabListPlayers: MutableList<TabListPlayer> = arrayListOf()
        for (i in 0..19){
            tabListPlayers.add(EmptyTabListPlayer())
        }
        return tabListPlayers
    }
}