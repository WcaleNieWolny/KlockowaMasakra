package pl.wolny.klockowamaskaradisguise.master.whitelist

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import pl.wolny.klockowamaskaradisguise.api.WhiteListController
import pl.wolny.klockowamaskaradisguise.master.whitelist.data.WhiteListDataFormat
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

class WhiteListController(private val dataFolder: File): Listener, WhiteListController {

    val whiteListDataFormat = WhiteListDataFormat(arrayListOf())
    val dataFile = File(dataFolder, "data.json")

    fun setup(){
        if(!dataFolder.exists()){
            dataFolder.mkdirs()
        }
        if(!dataFile.exists()){
            dataFile.createNewFile()
        }
        val data = dataFile.bufferedReader().readLine() ?: return
        val whiteListDataFormatFromFile = Json.decodeFromString<WhiteListDataFormat>(data)
        whiteListDataFormat.mutableList.addAll(whiteListDataFormatFromFile.mutableList)
    }

    private fun saveDataFile(){
        try {
            val writer = BufferedWriter(FileWriter(dataFile))
            writer.append(Json.encodeToString(whiteListDataFormat))
            writer.close()
        } catch (exception: Exception) {
            throw RuntimeException("Can not save plugin data file!")
        }
    }

    fun addToWhitelist(name: String){
        whiteListDataFormat.mutableList.add(name)
        saveDataFile()
    }

    fun removeFromWhiteList(name: String){
        whiteListDataFormat.mutableList.remove(name)
        saveDataFile()
    }

    fun enableWhiteList(){
        if(whiteListDataFormat.enabled){
            return
        }
        whiteListDataFormat.enabled = true
        saveDataFile()
    }

    fun disableWhiteList(){
        if(!whiteListDataFormat.enabled){
            return
        }
        whiteListDataFormat.enabled = false
        saveDataFile()
    }

    fun isEnabled() = whiteListDataFormat.enabled

    fun listWhitelistedPlayers() = whiteListDataFormat.mutableList

    override fun isWhitelisted(name: String) = whiteListDataFormat.mutableList.contains(name)

    @EventHandler
    fun onConnectEvent(event: AsyncPlayerPreLoginEvent){
        if(!whiteListDataFormat.enabled){
            return
        }
        if(!isWhitelisted(event.playerProfile.name!!)){
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, Component.text("You are not whitelisted!", NamedTextColor.RED))
        }
    }

}