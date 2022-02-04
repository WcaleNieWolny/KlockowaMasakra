package pl.wolny.kwadratowamasakratablist.render

import com.comphenix.protocol.wrappers.EnumWrappers
import com.comphenix.protocol.wrappers.PlayerInfoData
import com.comphenix.protocol.wrappers.WrappedGameProfile
import org.bukkit.entity.Player
import pl.wolny.kwadratowamasakratablist.extension.sendTabListEntity
import pl.wolny.kwadratowamasakratablist.model.frame.Frame
import pl.wolny.kwadratowamasakratablist.model.player.TabListPlayer
import java.util.*

class RenderController(private val latency: Int) {

    companion object{
        const val UUID_PATTERN = "00000000-0000-%s-0000-000000000000"
    }

    private val frameList: MutableList<Frame> = arrayListOf()

    fun registerFrame(frame: Frame){
        frameList.add(frame)
    }

    fun render(player: Player){
        var index = 0
        val playerInfoData: MutableList<PlayerInfoData> = arrayListOf()
        frameList.forEach{
            println(it::class.java.name)
            val entries = it.render(player)
            index = rawRender(entries, index, false, playerInfoData)
        }
        player.sendTabListEntity(playerInfoData)
    }

    private fun rawRender(tabListPlayers: List<TabListPlayer>, preIndex: Int, update: Boolean, playerInfoData: MutableList<PlayerInfoData>): Int{
        var index = preIndex
        tabListPlayers.forEach{
            if(index > 80){
                throw IllegalStateException("Index can't exceed 80!")
            }
            val fakePlayer = PlayerInfoData(
                WrappedGameProfile(UUID.fromString(String.format(UUID_PATTERN, formatIndex(index))), ""),
                latency,
                EnumWrappers.NativeGameMode.ADVENTURE,
                it.name()
            )
            fakePlayer.profile.properties.put("textures", it.skin())
            playerInfoData.add(fakePlayer)
            index++
        }
        return index
    }

    private fun formatIndex(int: Int): String = if(int < 9) "0$int" else "$int"

}