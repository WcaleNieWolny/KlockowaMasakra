package pl.wolny.klockowamasakralifes.api

import org.bukkit.entity.Player

interface LivesAPI {
    fun getLives(player: Player): Int
    fun getFormattedLives(player: Player): String
}