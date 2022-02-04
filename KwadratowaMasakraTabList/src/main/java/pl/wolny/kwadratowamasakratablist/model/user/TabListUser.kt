package pl.wolny.kwadratowamasakratablist.model.user

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.bukkit.Bukkit
import java.util.*

@Serializable
data class TabListUser(
    val name: String,
    val money: Double,
    @Transient var deaths: Int = 0,
    @Transient var uuid: UUID = UUID.randomUUID()
) {
    fun isOnline(): Boolean = Bukkit.getPlayer(name) != null
}