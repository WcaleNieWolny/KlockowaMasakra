package pl.wolny.kwadratowamasakratablist.extension

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.wrappers.EnumWrappers
import com.comphenix.protocol.wrappers.PlayerInfoData
import com.comphenix.protocol.wrappers.WrappedChatComponent
import com.comphenix.protocol.wrappers.WrappedGameProfile
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import pl.wolny.kwadratowamasakratablist.render.RenderController
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