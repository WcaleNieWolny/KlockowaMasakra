package pl.wolny.klockowamaskaradisguise

import kr.entree.spigradle.annotations.SpigotPlugin
import org.bukkit.Bukkit
import org.bukkit.entity.EntityType
import org.bukkit.plugin.java.JavaPlugin
import pl.wolny.klockowamaskaradisguise.master.whitelist.WhiteListCommand
import pl.wolny.klockowamaskaradisguise.master.whitelist.WhiteListController

@SpigotPlugin
class KlockowaMaskaraDisguise: JavaPlugin() {

    val whiteListController = WhiteListController(dataFolder)

    override fun onEnable() {
        whiteListController.setup()
        getCommand("serverwhitelist")!!.setExecutor(WhiteListCommand(whiteListController))
        Bukkit.getPluginManager().registerEvents(whiteListController, this)
    }

    override fun onDisable() {

    }
}