package pl.wolny.kwadratowamasakratablist.model.player

import com.comphenix.protocol.wrappers.WrappedChatComponent
import com.comphenix.protocol.wrappers.WrappedSignedProperty
import org.bukkit.entity.Player
import pl.wolny.kwadratowamasakratablist.extension.createTabListName
import pl.wolny.kwadratowamasakratablist.hook.VaultHook
import kotlin.streams.toList

class RealTabListPlayer(val player: Player, vaultHook: VaultHook): AbstractColoredTabListPlayer(player.createTabListName(vaultHook)) {
    override fun skin(): WrappedSignedProperty {
        val playerProperty = player.playerProfile.properties.stream().filter { it.name == "textures" }.toList()
        if(playerProperty.isEmpty()){
            return WrappedSignedProperty("textures", AbstractEmptyTabListPlayer.skinValue, AbstractEmptyTabListPlayer.skinSignature)
        }
        return WrappedSignedProperty("textures", playerProperty[0].value, playerProperty[0].signature)
    }

}