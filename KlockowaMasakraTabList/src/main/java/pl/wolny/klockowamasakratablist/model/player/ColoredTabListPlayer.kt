package pl.wolny.klockowamasakratablist.model.player

import com.comphenix.protocol.wrappers.WrappedSignedProperty

class ColoredTabListPlayer(name: String): AbstractColoredTabListPlayer(name) {
    override fun skin(): WrappedSignedProperty {
        return WrappedSignedProperty("textures", AbstractEmptyTabListPlayer.skinValue, AbstractEmptyTabListPlayer.skinSignature)
    }
}