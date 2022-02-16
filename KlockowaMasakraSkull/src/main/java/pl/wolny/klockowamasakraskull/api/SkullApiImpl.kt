package pl.wolny.klockowamasakraskull.api

import org.bukkit.entity.Player
import pl.wolny.klockowamasakraskull.controller.SkullPlayerController

class SkullApiImpl(private val skullPlayerController: SkullPlayerController): SkullApi {
    override fun hasSkull(player: Player): Boolean {
        return skullPlayerController.haveSkull(player.uniqueId)
    }
}