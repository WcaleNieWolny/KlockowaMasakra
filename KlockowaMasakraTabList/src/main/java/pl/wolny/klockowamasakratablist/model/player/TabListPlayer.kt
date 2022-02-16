package pl.wolny.klockowamasakratablist.model.player

import com.comphenix.protocol.wrappers.WrappedChatComponent
import com.comphenix.protocol.wrappers.WrappedSignedProperty

interface TabListPlayer {
    fun skin() : WrappedSignedProperty
    fun name(): WrappedChatComponent
}