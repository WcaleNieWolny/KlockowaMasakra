package pl.wolny.klockowamasakratablist.render

import org.bukkit.entity.Player
import pl.wolny.klockowamasakratablist.extension.formatLegacyString
import pl.wolny.klockowamasakratablist.hook.VaultHook

class FooterHeaderRenderController(private val serverBrand: String, private val serverFooter: String, private val vaultHook: VaultHook) {
    fun renderHeader() = formatLegacyString(serverBrand)
    fun renderFooter(player: Player): String{
        if(vaultHook.isAvailable()){
            return formatLegacyString(serverFooter.replace("#MONEY", vaultHook.provider()!!.getBalance(player).toString()))
        }
        return ""
    }
}