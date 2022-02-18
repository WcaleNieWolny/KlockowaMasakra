package pl.wolny.klockowamasakratablist.hook

import org.bukkit.Bukkit
import org.bukkit.plugin.RegisteredServiceProvider
import pl.wolny.klockowamasakralifes.api.LivesAPI

class LivesHook: PluginHook<LivesAPI> {

    private var provider: LivesAPI? = null
    private var available = false

    override fun isAvailable(): Boolean {
        return available
    }

    override fun setup() {
        val livesPlugin = Bukkit.getServer().pluginManager.getPlugin("KlockowaMasakraLifes") ?: return
        if (!livesPlugin.isEnabled) {
            return
        }
        val rsp: RegisteredServiceProvider<LivesAPI> = Bukkit.getServer().servicesManager.getRegistration(LivesAPI::class.java) ?: return
        provider = rsp.provider
        available = true
    }

    override fun provider(): LivesAPI {
        if(!isAvailable()){
            throw IllegalStateException("Can't acces Vault provider! Hook is not Available")
        }
        return provider!!
    }
}