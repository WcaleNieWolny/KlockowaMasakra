package pl.wolny.klockowamasakraskull.hook

import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.plugin.RegisteredServiceProvider
import org.bukkit.plugin.java.JavaPlugin

class VaultHook(private val plugin: JavaPlugin) {

    var economy: Economy? = null

    fun setup(){
        val vault = Bukkit.getServer().pluginManager.getPlugin("Vault") ?: return
        if (!vault.isEnabled) {
            Bukkit.getLogger().info("Vault is disabled!")
            Bukkit.getPluginManager().disablePlugin(plugin)
        }
        val rsp: RegisteredServiceProvider<Economy>? = Bukkit.getServer().servicesManager.getRegistration(Economy::class.java)
        if (rsp == null) {
            Bukkit.getLogger().info("Vault is disabled!")
            Bukkit.getPluginManager().disablePlugin(plugin)
            return
        }
        economy = rsp.provider
    }

}