package pl.wolny.kwadratowamasakratablist.render

import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import pl.wolny.kwadratowamasakratablist.extension.formatComponent
import pl.wolny.kwadratowamasakratablist.extension.formatLegacyString
import pl.wolny.kwadratowamasakratablist.hook.VaultHook

class FooterHeaderRenderController(private val serverBrand: String, private val serverFooter: String, private val vaultHook: VaultHook) {
    fun renderHeader() = formatLegacyString(serverBrand)
    fun renderFooter(player: Player): String{
        if(vaultHook.isAvailable()){
            return formatLegacyString(serverFooter.replace("#MONEY", vaultHook.provider()!!.getBalance(player).toString()))
        }
        return ""
    }
}