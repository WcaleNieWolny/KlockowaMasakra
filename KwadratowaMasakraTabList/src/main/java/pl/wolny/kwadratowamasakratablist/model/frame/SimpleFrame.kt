package pl.wolny.kwadratowamasakratablist.model.frame

import pl.wolny.kwadratowamasakratablist.model.player.EmptyTabListPlayer
import pl.wolny.kwadratowamasakratablist.model.player.TabListPlayer

class SimpleFrame: Frame {
    override fun render(): List<TabListPlayer> {
        return listOf(EmptyTabListPlayer(), EmptyTabListPlayer(), EmptyTabListPlayer())
    }
}