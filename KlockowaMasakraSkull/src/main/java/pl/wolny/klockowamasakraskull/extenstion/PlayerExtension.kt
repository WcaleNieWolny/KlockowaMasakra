package pl.wolny.klockowamasakraskull.extenstion

import org.bukkit.NamespacedKey
import org.bukkit.OfflinePlayer
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin
import pl.wolny.klockowamasakraskull.KlockowaMasakraSkull

object PlayerExtension {
    val plugin = JavaPlugin.getPlugin(KlockowaMasakraSkull::class.java)
}

//fun<T : PersistentDataType<Any?, R>, R> Player.getData(key: String, type: T): R? {
//    return player?.persistentDataContainer?.get(
//        NamespacedKey(PlayerExtension.plugin, key),
//        type
//    )
//}
fun <Z, R> OfflinePlayer.getData(key: String, type: PersistentDataType<Z, R>): R?{
    return player?.persistentDataContainer?.get(
        NamespacedKey(PlayerExtension.plugin, key),
        type
    )
}

fun <Z, R> OfflinePlayer.setData(key: String, type: PersistentDataType<Z, R>, value: R){
    player?.persistentDataContainer?.set(
        NamespacedKey(PlayerExtension.plugin, key),
        type,
        value
    )
}

fun OfflinePlayer.removeData(key: String){
    player?.persistentDataContainer?.remove(
        NamespacedKey(PlayerExtension.plugin, key)
    )
}