package pl.wolny.kwadratowamasakratablist.model.frame

import org.bukkit.entity.Player
import pl.wolny.kwadratowamasakratablist.hook.FunnyGuildsHook
import pl.wolny.kwadratowamasakratablist.model.player.ColoredTabListPlayer
import pl.wolny.kwadratowamasakratablist.model.player.EmptyTabListPlayer
import pl.wolny.kwadratowamasakratablist.model.player.TabListPlayer

class GuildsFrame(private val funnyGuildsHook: FunnyGuildsHook): Frame {
    override fun render(player: Player): List<TabListPlayer> {
        val index = 1
        val returnList: MutableList<TabListPlayer> = arrayListOf()

        returnList.add(ColoredTabListPlayer("&aGildie"))
        returnList.add(EmptyTabListPlayer())

        val guilds = funnyGuildsHook.provider().guildManager.guilds.sortedBy { it.rank.points }

        for(i in index..18){
            val guild = guilds.getOrNull(i-1)
            if(guild != null) {
                returnList.add(ColoredTabListPlayer("&f&l${guild.tag} &7${guild.name}"))
            }else{
                returnList.add(EmptyTabListPlayer())
            }
        }

        return returnList
    }
}