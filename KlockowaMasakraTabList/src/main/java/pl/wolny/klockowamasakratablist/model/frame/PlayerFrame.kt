package pl.wolny.klockowamasakratablist.model.frame

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import pl.wolny.klockowamasakratablist.extension.getAvailablePlayers
import pl.wolny.klockowamasakratablist.extension.getBalance
import pl.wolny.klockowamasakratablist.extension.getGuild
import pl.wolny.klockowamasakratablist.hook.FunnyGuildsHook
import pl.wolny.klockowamasakratablist.hook.LivesHook
import pl.wolny.klockowamasakratablist.hook.SkullHook
import pl.wolny.klockowamasakratablist.hook.VaultHook
import pl.wolny.klockowamasakratablist.model.player.ColoredTabListPlayer
import pl.wolny.klockowamasakratablist.model.player.EmptyTabListPlayer
import pl.wolny.klockowamasakratablist.model.player.RealTabListPlayer
import pl.wolny.klockowamasakratablist.model.player.TabListPlayer
import pl.wolny.klockowamasakratablist.model.user.TabListRepository
import pl.wolny.klockowamasakratablist.model.user.TabListUser
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

class PlayerFrame(
    private val vaultHook: VaultHook,
    private val tabListRepository: TabListRepository,
    private val plugin: JavaPlugin,
    private val skullHook: SkullHook,
    private val funnyGuildsHook: FunnyGuildsHook
) : Frame {

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
            tabListPlayers.add(RealTabListPlayer(availablePlayer, vaultHook, skullHook, funnyGuildsHook))
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
        val stringBuilder = StringBuilder()
        val offlinePlayer = Bukkit.getOfflinePlayer(tabListUser.uuid)
        if (vaultHook.isAvailable()) {
            stringBuilder.append("&7${vaultHook.provider()?.getBalance(offlinePlayer)} ")
        }

        stringBuilder.append(offlinePlayer.getGuild(funnyGuildsHook))
        stringBuilder.append("&8${tabListUser.name}")

        return ColoredTabListPlayer(stringBuilder.toString())
    }
}