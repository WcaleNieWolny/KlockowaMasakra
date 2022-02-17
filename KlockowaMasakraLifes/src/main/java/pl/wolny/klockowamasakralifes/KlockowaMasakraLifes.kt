package pl.wolny.klockowamasakralifes

import org.bukkit.Bukkit
import org.bukkit.plugin.ServicePriority
import org.bukkit.plugin.java.JavaPlugin
import pl.wolny.klockowamasakralifes.api.LivesAPI
import pl.wolny.klockowamasakralifes.api.LivesApiImpl
import pl.wolny.klockowamasakralifes.data.UserDataController

class KlockowaMasakraLifes: JavaPlugin() {

    private val userDataController = UserDataController(this)

    override fun onEnable() {
        Bukkit.getServicesManager().register(LivesAPI::class.java, LivesApiImpl(userDataController), this, ServicePriority.Normal)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}