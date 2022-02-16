package pl.wolny.klockowamasakratablist.model.frame

import org.bukkit.entity.Player
import pl.wolny.klockowamasakratablist.model.player.EmptyTabListPlayer
import pl.wolny.klockowamasakratablist.model.player.TabListPlayer

class EmptyFrame: Frame {
    override fun render(player: Player): List<TabListPlayer> {
        val tabListPlayers: MutableList<TabListPlayer> = arrayListOf()
        for (i in 0..19){
            tabListPlayers.add(EmptyTabListPlayer())
        }
        return tabListPlayers
    }
}