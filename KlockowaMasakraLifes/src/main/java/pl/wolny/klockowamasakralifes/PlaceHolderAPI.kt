package pl.wolny.klockowamasakralifes

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player

class PlaceHolderAPI(plugin: KlockowaMasakraLifes) : PlaceholderExpansion() {
    private val plugin: KlockowaMasakraLifes

    init {
        this.plugin = plugin
    }

    override fun getAuthor(): String {
        return "WcaleNieWolny"
    }

    override fun getIdentifier(): String {
        return "klockowa-lifes-system"
    }

    override fun getVersion(): String {
        return "1.0.0"
    }

    override fun persist(): Boolean {
        return true // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }

    override fun onPlaceholderRequest(player: Player?, params: String): String? {
        if (params.contains("klockowa-lifes", ignoreCase = true)) {
            if (player == null) {
                return "unknown"
            }
            return plugin.userController.getLives(player).toString()
        }
        return null
    }
}