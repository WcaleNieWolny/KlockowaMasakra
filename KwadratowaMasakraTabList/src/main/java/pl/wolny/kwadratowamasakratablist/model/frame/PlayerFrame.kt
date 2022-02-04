package pl.wolny.kwadratowamasakratablist.model.frame

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import pl.wolny.kwadratowamasakratablist.extension.getAvailablePlayers
import pl.wolny.kwadratowamasakratablist.extension.getBalance
import pl.wolny.kwadratowamasakratablist.hook.VaultHook
import pl.wolny.kwadratowamasakratablist.model.player.ColoredTabListPlayer
import pl.wolny.kwadratowamasakratablist.model.player.EmptyTabListPlayer
import pl.wolny.kwadratowamasakratablist.model.player.RealTabListPlayer
import pl.wolny.kwadratowamasakratablist.model.player.TabListPlayer
import pl.wolny.kwadratowamasakratablist.model.user.TabListRepository
import pl.wolny.kwadratowamasakratablist.model.user.TabListUser
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

class PlayerFrame(private val vaultHook: VaultHook, private val tabListRepository: TabListRepository, private val plugin: JavaPlugin) : Frame {

    override fun render(player: Player): List<TabListPlayer> {
        val tabListPlayers: MutableList<TabListPlayer> = arrayListOf()
        var index = 2

        var availablePlayers: List<Player>// = player.getAvailablePlayers()
        val playerCompletableFuture: CompletableFuture<List<Player>> = CompletableFuture()
        Bukkit.getScheduler().runTask(plugin, Runnable {
            playerCompletableFuture.complete(player.getAvailablePlayers())
        })
        availablePlayers = playerCompletableFuture.get(3000, TimeUnit.MILLISECONDS)


        if (vaultHook.isAvailable()) {
            availablePlayers = availablePlayers.sortedBy { it.getBalance(vaultHook) }.reversed()
        }

        for (availablePlayer in availablePlayers) {
            tabListPlayers.add(RealTabListPlayer(availablePlayer, vaultHook))
            index++
            if (index == 39) {
                if ((availablePlayers.size - 38) != 0) {
                    tabListPlayers.add(ColoredTabListPlayer("... I ${availablePlayers.size - 38} jeszcze ..."))
                    return tabListPlayers
                }
            }
        }

        val offlinePlayers = tabListRepository.tabListUsers.filter { !it.isOnline() }.sortedBy { it.money }
        val slotLeft = 38-index
        for (i in 0..slotLeft){
            val offlinePlayer = offlinePlayers.getOrNull(i)
            if(offlinePlayer != null){
                tabListPlayers.add(createPrivateUser(offlinePlayer))
                index++
            }
        }
        if(offlinePlayers.size > slotLeft){
            val overflow = offlinePlayers.size - slotLeft
            if(overflow == 1){
                tabListPlayers.add(createPrivateUser(offlinePlayers[37]))
                return tabListPlayers
            }
            tabListPlayers.add(ColoredTabListPlayer("... I $overflow jeszcze ..."))
            return tabListPlayers
        }
        for (i in 0..39 - index) {
            tabListPlayers.add(EmptyTabListPlayer())
        }
        return tabListPlayers
    }

    private fun createPrivateUser(tabListUser: TabListUser): TabListPlayer{
        return ColoredTabListPlayer(
            (if (vaultHook.isAvailable()) "&7${
                vaultHook.provider()?.getBalance(Bukkit.getOfflinePlayer(tabListUser.uuid))
            } " else "") +
                    "&8${tabListUser.name}"
        )
    }
}