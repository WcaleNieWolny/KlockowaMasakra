package pl.wolny.klockowamasakratablist.hook

import net.dzikoysk.funnyguilds.FunnyGuilds
import net.dzikoysk.funnyguilds.user.User
import net.dzikoysk.funnyguilds.user.UserManager
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.scheduler.BukkitTask
import java.lang.reflect.Field
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class FunnyGuildsHook: PluginHook<FunnyGuilds> {

    private var available = false
    private var provider: FunnyGuilds? = null
    private lateinit var userManagerClass: Class<out UserManager>
    private lateinit var userManagerUsers: Field

    override fun isAvailable(): Boolean {
        return available
    }

    override fun setup() {
        val funnyGuildsPlugin = Bukkit.getServer().pluginManager.getPlugin("FunnyGuilds") ?: return
        if (!funnyGuildsPlugin.isEnabled) {
            return
        }

        provider = FunnyGuilds.getInstance()
        available = true

        val taskField: Field = provider!!::class.java.getDeclaredField("tablistBroadcastTask")
        taskField.isAccessible = true
        (taskField.get(provider) as BukkitTask).cancel()

        userManagerClass = provider!!.userManager::class.java
        userManagerUsers = userManagerClass.getDeclaredField("usersByUuid")
        userManagerUsers.isAccessible = true
    }

    override fun provider(): FunnyGuilds {
        if(!isAvailable()){
            throw IllegalStateException("Can't acces FunnyGuilds Provider! Hook is not Available")
        }
        return provider!!
    }

    fun getUser(player: OfflinePlayer): User?{
        if(!isAvailable()){
            throw IllegalStateException("Can't acces FunnyGuilds Provider! Hook is not Available")
        }
        val hashMap = userManagerUsers.get(provider!!.userManager) as ConcurrentHashMap<UUID, User>
        return hashMap[player.uniqueId]
    }
}