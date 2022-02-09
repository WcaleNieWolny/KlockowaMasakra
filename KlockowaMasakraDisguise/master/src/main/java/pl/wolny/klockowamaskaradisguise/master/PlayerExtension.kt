package pl.wolny.klockowamaskaradisguise.master

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

fun CommandSender.sendFormatedMessage(string: String){
    sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(string))
}