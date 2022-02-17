package pl.wolny.klockowamasakralifes.api

import org.bukkit.entity.Player
import pl.wolny.klockowamasakralifes.data.UserDataController

class LivesApiImpl(private val userDataController: UserDataController): LivesAPI {
    override fun getLives(player: Player): Int {
        return userDataController.getLives(player)
    }
}