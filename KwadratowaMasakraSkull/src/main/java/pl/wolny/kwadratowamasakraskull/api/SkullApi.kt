package pl.wolny.kwadratowamasakraskull.api

import org.bukkit.entity.Player
import pl.wolny.kwadratowamasakraskull.model.SkullPlayer

interface SkullApi {
    fun hasSkull(player: Player): Boolean
}