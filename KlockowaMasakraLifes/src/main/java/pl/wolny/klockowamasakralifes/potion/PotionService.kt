package pl.wolny.klockowamasakralifes.potion

import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin

class PotionService(
    private val plugin: JavaPlugin,
    private val red: Int,
    private val green: Int,
    private val blue: Int
) {
    fun createPotion(): ItemStack{
        val item = ItemStack(Material.POTION)
        val meta = item.itemMeta as PotionMeta
        meta.color = Color.fromBGR(blue, green, red)
        item.itemMeta = meta
        item.itemMeta.persistentDataContainer.set(
            NamespacedKey(plugin, "WOLNY_CUSTOM_POTION"),
            PersistentDataType.SHORT,
            1
        )
        return item
    }
}