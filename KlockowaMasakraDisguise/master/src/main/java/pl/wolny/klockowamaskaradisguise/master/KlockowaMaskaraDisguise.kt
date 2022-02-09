package pl.wolny.klockowamaskaradisguise

import kr.entree.spigradle.annotations.SpigotPlugin
import org.bukkit.entity.EntityType
import org.bukkit.plugin.java.JavaPlugin
import pl.wolny.klockowamaskaradisguise.master.whitelist.WhiteListController

@SpigotPlugin
class KlockowaMaskaraDisguise: JavaPlugin() {

    val whiteListController = WhiteListController(dataFolder)

    override fun onEnable() {

    }

    override fun onDisable() {

    }
}