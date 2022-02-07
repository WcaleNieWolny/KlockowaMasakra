package pl.wolny.kwadratowamasakraskull.extenstion

import kotlinx.serialization.ExperimentalSerializationApi
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin
import pl.wolny.kwadratowamasakraskull.KwadratowaMasakraSkull
import pl.wolny.kwadratowamasakraskull.data.SkullPlayerPersistentDataType
import pl.wolny.kwadratowamasakraskull.model.SkullPlayer
import java.util.*

object PlayerExtension {
    val plugin = JavaPlugin.getPlugin(KwadratowaMasakraSkull::class.java)
}

//fun<T : PersistentDataType<Any?, R>, R> Player.getData(key: String, type: T): R? {
//    return player?.persistentDataContainer?.get(
//        NamespacedKey(PlayerExtension.plugin, key),
//        type
//    )
//}
fun <Z, R> Player.getData(key: String, type: PersistentDataType<Z, R>): R?{
    return player?.persistentDataContainer?.get(
        NamespacedKey(PlayerExtension.plugin, key),
        type
    )
}

fun <Z, R> Player.setData(key: String, type: PersistentDataType<Z, R>, value: R){
    player?.persistentDataContainer?.set(
        NamespacedKey(PlayerExtension.plugin, key),
        type,
        value
    )
}

@ExperimentalSerializationApi
fun Player.haveSkull(): Boolean{
    val skullPlayer: SkullPlayer = player?.getData("wolnylogin.SkullPlayer", SkullPlayerPersistentDataType.get()) ?: return false
    val current = Date(System.currentTimeMillis())
    val skullDate = Date(skullPlayer.skullTime)
    if(current.after(skullDate)){
        return false
    }
    return skullPlayer.haveSkull
}

@ExperimentalSerializationApi
fun Player.setSkull(skullPlayer: SkullPlayer){
    player?.setData("wolnylogin.SkullPlayer", SkullPlayerPersistentDataType.get(), skullPlayer)
}