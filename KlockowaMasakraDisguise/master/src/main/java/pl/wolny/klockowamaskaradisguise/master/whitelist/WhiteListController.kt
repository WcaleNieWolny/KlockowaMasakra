package pl.wolny.klockowamaskaradisguise.master.whitelist

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.event.EventHandler
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import pl.wolny.klockowamaskaradisguise.master.whitelist.data.WhiteListDataFormat
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

class WhiteListController(private val dataFolder: File) {

    val whiteListDataFormat = WhiteListDataFormat(arrayListOf())
    val dataFile = File(dataFolder, "data.json")
    var kickOnJoin = true

    fun setup(){
        if(!dataFolder.exists()){
            dataFolder.mkdirs()
        }
        if(dataFile.exists()){
            dataFile.createNewFile()
        }
        val whiteListDataFormatFromFile = Json.decodeFromString<WhiteListDataFormat>(dataFile.bufferedReader().readLine())
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

    fun listWhitelistedPlayers() = whiteListDataFormat.mutableList

    fun isWhitelisted(name: String) = whiteListDataFormat.mutableList.contains(name)

    @EventHandler()
    fun onConnectEvent(event: AsyncPlayerPreLoginEvent){
        if(!kickOnJoin){
            return
        }
        if(!isWhitelisted(event.playerProfile.name!!)){
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, Component.text("You are not whitelisted!", NamedTextColor.RED))
        }
    }

}