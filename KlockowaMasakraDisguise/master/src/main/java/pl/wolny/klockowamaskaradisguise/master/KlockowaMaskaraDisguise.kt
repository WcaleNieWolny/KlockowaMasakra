package pl.wolny.klockowamaskaradisguise

import kr.entree.spigradle.annotations.SpigotPlugin
import org.bukkit.Bukkit
import org.bukkit.entity.EntityType
import org.bukkit.plugin.java.JavaPlugin
import pl.wolny.klockowamaskaradisguise.master.TestCommand
import pl.wolny.klockowamaskaradisguise.master.controller.FakePlayerController
import pl.wolny.klockowamaskaradisguise.master.utils.MinecraftUtils
import pl.wolny.klockowamaskaradisguise.master.whitelist.WhiteListCommand
import pl.wolny.klockowamaskaradisguise.master.whitelist.WhiteListController

@SpigotPlugin
class KlockowaMaskaraDisguise: JavaPlugin() {

    private val whiteListController = WhiteListController(dataFolder)
    private val fakePlayerController = FakePlayerController()

    override fun onEnable() {
        //MinecraftUtils.setupRuntime()
        whiteListController.setup()

        getCommand("serverwhitelist")!!.setExecutor(WhiteListCommand(whiteListController))
        getCommand("testcmd")!!.setExecutor(TestCommand(fakePlayerController))
        Bukkit.getPluginManager().registerEvents(whiteListController, this)
        Bukkit.getPluginManager().registerEvents(fakePlayerController, this)
    }

    override fun onDisable() {

    }
}