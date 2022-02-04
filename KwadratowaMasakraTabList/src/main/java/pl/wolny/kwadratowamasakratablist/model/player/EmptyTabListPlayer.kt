package pl.wolny.kwadratowamasakratablist.model.player

import com.comphenix.protocol.wrappers.WrappedChatComponent

class EmptyTabListPlayer: AbstractEmptyTabListPlayer() {
    override fun name(): WrappedChatComponent {
        return WrappedChatComponent.fromText(" ")
    }
}