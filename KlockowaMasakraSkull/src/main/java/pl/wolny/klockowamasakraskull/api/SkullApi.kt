package pl.wolny.klockowamasakraskull.api

import org.bukkit.entity.Player

interface SkullApi {
    fun hasSkull(player: Player): Boolean
}