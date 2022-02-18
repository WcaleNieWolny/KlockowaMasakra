package pl.wolny.klockowamasakratablist.render

import org.bukkit.entity.Player
import pl.wolny.klockowamasakratablist.extension.formatLegacyString
import pl.wolny.klockowamasakratablist.hook.LivesHook
import pl.wolny.klockowamasakratablist.hook.VaultHook

class FooterHeaderRenderController(private val serverBrand: String, private val serverFooter: String, private val vaultHook: VaultHook, private val livesHook: LivesHook) {
    fun renderHeader() = formatLegacyString(serverBrand)
    fun renderFooter(player: Player): String{
        val stringBuilder = StringBuilder()
        if(vaultHook.isAvailable()){
            stringBuilder.append(formatLegacyString(serverFooter.replace("#MONEY", vaultHook.provider()!!.getBalance(player).toString())))
        }
        if(livesHook.isAvailable()){
            stringBuilder.append("\n")
            stringBuilder.append(formatLegacyString(livesHook.provider().getFormattedLives(player)))
        }
        return stringBuilder.toString()
    }
}