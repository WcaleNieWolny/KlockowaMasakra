package pl.wolny.kwadratowamasakratablist.model.frame

import pl.wolny.kwadratowamasakratablist.model.player.TabListPlayer

interface Frame {
    fun render(): List<TabListPlayer>
}