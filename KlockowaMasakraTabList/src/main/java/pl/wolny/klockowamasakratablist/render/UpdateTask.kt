package pl.wolny.klockowamasakratablist.render

import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

class UpdateTask(private val renderController: RenderController): BukkitRunnable() {

    override fun run() {
        for (onlinePlayer in Bukkit.getServer().onlinePlayers) {
            renderController.render(onlinePlayer, true)
        }
    }
}