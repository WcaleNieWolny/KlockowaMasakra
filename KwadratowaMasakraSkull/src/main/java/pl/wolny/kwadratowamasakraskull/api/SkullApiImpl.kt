package pl.wolny.kwadratowamasakraskull.api

import org.bukkit.entity.Player
import pl.wolny.kwadratowamasakraskull.controller.SkullPlayerController

class SkullApiImpl(private val skullPlayerController: SkullPlayerController): SkullApi {
    override fun hasSkull(player: Player): Boolean {
        return skullPlayerController.haveSkull(player.uniqueId)
    }
}