package pl.wolny.klockowamasakralifes.potion

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin
import pl.wolny.klockowamasakralifes.controller.UserController
import pl.wolny.klockowamasakralifes.formatMessage

class PotionService(
    private val userController: UserController,
    plugin: JavaPlugin,
    private val red: Int,
    private val green: Int,
    private val blue: Int,
    private val potionName: String
) : Listener {

    private val namespacedKey = NamespacedKey(plugin, "WOLNY_CUSTOM_POTION")

    fun createPotion(): ItemStack {
        val item = ItemStack(Material.POTION)

        val meta = item.itemMeta as PotionMeta

        with(meta){
            color = Color.fromBGR(blue, green, red)
            addItemFlags(ItemFlag.HIDE_POTION_EFFECTS)
            displayName(Component.empty().decoration(TextDecoration.ITALIC, false).append(formatMessage(potionName)))
            persistentDataContainer.set(
                namespacedKey,
                PersistentDataType.SHORT,
                1
            )
        }

        item.itemMeta = meta

        return item
    }

    @EventHandler
    fun onPotionDrink(event: PlayerItemConsumeEvent) {
        println("FUCK!")
        val item = event.item
        if (item.itemMeta.persistentDataContainer.has(
                namespacedKey,
                PersistentDataType.SHORT
            )
        ) {
            val player = event.player
            userController.setLives(player, userController.getLives(player) + 1)
        }
    }
}