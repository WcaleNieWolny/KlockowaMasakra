package pl.wolny.klockowamasakratablist.model.player

import com.comphenix.protocol.wrappers.WrappedSignedProperty
import org.bukkit.entity.Player
import pl.wolny.klockowamasakratablist.extension.createTabListName
import pl.wolny.klockowamasakratablist.hook.FunnyGuildsHook
import pl.wolny.klockowamasakratablist.hook.SkullHook
import pl.wolny.klockowamasakratablist.hook.VaultHook
import kotlin.streams.toList

class RealTabListPlayer(val player: Player, vaultHook: VaultHook, skullHook: SkullHook, funnyGuildsHook: FunnyGuildsHook): AbstractColoredTabListPlayer(player.createTabListName(vaultHook, skullHook, funnyGuildsHook)) {
    override fun skin(): WrappedSignedProperty {
        val playerProperty = player.playerProfile.properties.stream().filter { it.name == "textures" }.toList()
        if(playerProperty.isEmpty()){
            return WrappedSignedProperty("textures", AbstractEmptyTabListPlayer.skinValue, AbstractEmptyTabListPlayer.skinSignature)
        }
        return WrappedSignedProperty("textures", playerProperty[0].value, playerProperty[0].signature)
    }

}