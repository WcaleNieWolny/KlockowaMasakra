package pl.wolny.klockowamasakralifes.api

import org.bukkit.entity.Player
import pl.wolny.klockowamasakralifes.controller.UserController

class LivesApiImpl(
    private val userController: UserController,
    private val hearthSymbol: String,
    private val hearthColour: String,
    private val livesOverflow: String,
    private val noLivesAvailable: String
) : LivesAPI {
    override fun getLives(player: Player): Int {
        return userController.getLives(player)
    }

    override fun getFormattedLives(player: Player): String {
        val lives = getLives(player)
        if (lives > 10) {
            return livesOverflow
                .replace("#LIVES#", lives.toString())
                .replace("#HEARTH_SYMBOL#", hearthSymbol)
                .replace("#HEARTH_COLOUR#", hearthColour)
        }
        if (lives == 0) {
            return noLivesAvailable
        }
        val stringBuilder = StringBuilder()
        for (i in 1..lives) {
            stringBuilder.append("$hearthColour$hearthSymbol ")
        }
        return stringBuilder.toString().trim()
    }
}