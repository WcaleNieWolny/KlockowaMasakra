package pl.wolny.kwadratowamasakratablist.hook

import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.plugin.RegisteredServiceProvider
import pl.wolny.kwadratowamasakraskull.api.SkullApi

class SkullHook: PluginHook<SkullApi?> {

    private var available: Boolean = false
    private var skullApi: SkullApi? = null

    override fun isAvailable(): Boolean {
        return available
    }

    override fun setup() {
        val skullPlugin = Bukkit.getServer().pluginManager.getPlugin("KwadratowaMasakraSkull") ?: return
        if (!skullPlugin.isEnabled) {
            return
        }
        val rsp: RegisteredServiceProvider<SkullApi> = Bukkit.getServer().servicesManager.getRegistration(SkullApi::class.java) ?: return
        skullApi = rsp.provider
        available = true
    }

    override fun provider(): SkullApi? {
        return skullApi
    }
}