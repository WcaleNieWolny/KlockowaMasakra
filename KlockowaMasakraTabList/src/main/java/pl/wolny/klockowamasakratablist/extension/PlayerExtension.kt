package pl.wolny.klockowamasakratablist.extension

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.wrappers.EnumWrappers
import com.comphenix.protocol.wrappers.PlayerInfoData
import com.comphenix.protocol.wrappers.WrappedChatComponent
import com.comphenix.protocol.wrappers.WrappedGameProfile
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import pl.wolny.klockowamasakratablist.hook.FunnyGuildsHook
import pl.wolny.klockowamasakratablist.hook.SkullHook
import pl.wolny.klockowamasakratablist.hook.VaultHook
import java.util.*

fun Player.sendTabListEntity(profileInfoData: List<PlayerInfoData>){
    val packet = PacketContainer(PacketType.Play.Server.PLAYER_INFO)
    packet.playerInfoDataLists.write(0, profileInfoData)
    packet.playerInfoAction.write(0, EnumWrappers.PlayerInfoAction.ADD_PLAYER)
    ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet)
}
fun Player.sendTabListEntity(profileInfoData: PlayerInfoData){
    player?.sendTabListEntity(listOf(profileInfoData))
}
fun Player.removeTabListEntity(profileInfoData: List<PlayerInfoData>){
    val packet = PacketContainer(PacketType.Play.Server.PLAYER_INFO)
    packet.playerInfoDataLists.write(0, profileInfoData)
    packet.playerInfoAction.write(0, EnumWrappers.PlayerInfoAction.REMOVE_PLAYER)
    ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet)
}
fun Player.updateTabListEntity(profileInfoData: List<PlayerInfoData>){
    val packet = PacketContainer(PacketType.Play.Server.PLAYER_INFO)
    packet.playerInfoDataLists.write(0, profileInfoData)
    packet.playerInfoAction.write(0, EnumWrappers.PlayerInfoAction.UPDATE_DISPLAY_NAME)
    ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet)
}
fun Player.removeTabListEntity(profileInfoData: PlayerInfoData){
    player?.removeTabListEntity(listOf(profileInfoData))
}
fun Player.removeTabListEntityByUUID(uuid: UUID){
    player?.removeTabListEntity(PlayerInfoData(
        WrappedGameProfile(uuid, ""),
        0,
        EnumWrappers.NativeGameMode.NOT_SET,
        WrappedChatComponent.fromText("")
    ))
}
fun Player.getAvailablePlayers(): List<Player> {
    return Bukkit.getServer().onlinePlayers.filter { player?.canSee(it) == true }
}

fun OfflinePlayer.getGuild(funnyGuildsHook: FunnyGuildsHook): String {
    if(player == null){
        return ""
    }
    if (!funnyGuildsHook.isAvailable()) {
        return ""
    }
    val funnyUser = funnyGuildsHook.getUser(player!!) ?: return ""
    if (funnyUser.hasGuild()) {
        return "&3${funnyUser.guild.tag} "
    }
    return ""
}


fun Player.createTabListName(vaultHook: VaultHook, skullApi: SkullHook, funnyGuildsHook: FunnyGuildsHook): String{
    val stringBuilder = StringBuilder()
    if(vaultHook.isAvailable()){
        stringBuilder.append("&3${vaultHook.provider()?.getBalance(player) ?: 0} ")
    }

    val userGuild = player?.getGuild(funnyGuildsHook)
    if(userGuild != null){
        stringBuilder.append("$userGuild")
    }

    stringBuilder.append("&a${player?.displayName}")
    if(skullApi.isAvailable()){
        if(skullApi.provider()?.hasSkull(player!!) == true){
            stringBuilder.append("&f☠")
        }
    }

    return stringBuilder.toString()
}
fun Player.getBalance(vaultHook: VaultHook): Int{
    return vaultHook.provider()?.getBalance(player)?.toInt() ?: 0
}