package pl.wolny.klockowamasakratablist.model.frame

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import pl.wolny.klockowamasakratablist.extension.getAvailablePlayers
import pl.wolny.klockowamasakratablist.model.player.ColoredTabListPlayer
import pl.wolny.klockowamasakratablist.model.player.EmptyTabListPlayer
import pl.wolny.klockowamasakratablist.model.player.TabListPlayer

class PrePlayerFrame: Frame {
    override fun render(player: Player): List<TabListPlayer> {
        return listOf(
            ColoredTabListPlayer("&a&lGracze &f${player.getAvailablePlayers().size}&8/&7${Bukkit.getServer().maxPlayers}"),
            EmptyTabListPlayer()
        )
    }
}