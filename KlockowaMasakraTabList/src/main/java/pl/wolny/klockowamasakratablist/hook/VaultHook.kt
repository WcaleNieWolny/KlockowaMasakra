package pl.wolny.klockowamasakratablist.hook

import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit.getServer
import org.bukkit.plugin.RegisteredServiceProvider


class VaultHook: PluginHook<Economy?> {

    private var available: Boolean = false
    private var economy: Economy? = null

    override fun isAvailable(): Boolean {
        return available
    }

    override fun setup() {
        val vault = getServer().pluginManager.getPlugin("Vault") ?: return
        if (!vault.isEnabled) {
            return
        }
        val rsp: RegisteredServiceProvider<Economy> = getServer().servicesManager.getRegistration(Economy::class.java) ?: return
        economy = rsp.provider
        available = true
    }

    override fun provider(): Economy? {
        if(!isAvailable()){
            throw IllegalStateException("Can't acces Vault provider! Hook is not Available")
        }
        return economy
    }
}