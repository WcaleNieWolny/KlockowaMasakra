package pl.wolny.kwadratowamasakratablist.render

import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor

class UpdateTask(private val renderController: RenderController): BukkitRunnable() {

    override fun run() {
        for (onlinePlayer in Bukkit.getServer().onlinePlayers) {
            renderController.render(onlinePlayer, true)
        }
    }
}