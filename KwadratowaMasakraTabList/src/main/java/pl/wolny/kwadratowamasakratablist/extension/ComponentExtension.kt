package pl.wolny.kwadratowamasakratablist.extension

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.md_5.bungee.api.ChatColor

fun formatComponent(name: String) = LegacyComponentSerializer.legacyAmpersand().deserialize(name)

fun formatLegacyString(name: String) = ChatColor.translateAlternateColorCodes('&', name)