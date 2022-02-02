package pl.wolny.kwadratowamasakratablist.model.frame

import org.bukkit.entity.Player
import pl.wolny.kwadratowamasakratablist.hook.VaultHook
import pl.wolny.kwadratowamasakratablist.model.player.TabListPlayer

class PlayerFrame(private val vaultHook: VaultHook): Frame {
    override fun render(player: Player): List<TabListPlayer> {
        TODO("Not yet implemented")
    }
}