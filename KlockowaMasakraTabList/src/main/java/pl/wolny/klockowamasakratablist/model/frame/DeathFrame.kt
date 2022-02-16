package pl.wolny.klockowamasakratablist.model.frame

import org.bukkit.entity.Player
import pl.wolny.klockowamasakratablist.model.player.ColoredTabListPlayer
import pl.wolny.klockowamasakratablist.model.player.EmptyTabListPlayer
import pl.wolny.klockowamasakratablist.model.player.TabListPlayer
import pl.wolny.klockowamasakratablist.model.user.TabListRepository

class DeathFrame(private val repository: TabListRepository): Frame {
    override fun render(player: Player): List<TabListPlayer> {
        val index = 1
        val deathList = repository.tabListUsers.sortedBy { it.deaths }.reversed()
        val returnList: MutableList<TabListPlayer> = arrayListOf()
        returnList.add(ColoredTabListPlayer("&a&lŚmierci"))
        returnList.add(EmptyTabListPlayer())
        for(i in index..18){
            val user = deathList.getOrNull(i-1)
            if(user != null) {
                returnList.add(ColoredTabListPlayer("&3${user.deaths} &7${user.name}"))
            }else{
                returnList.add(EmptyTabListPlayer())
            }
        }
        return returnList
    }
}