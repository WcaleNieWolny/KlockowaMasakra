package pl.wolny.klockowamaskaradisguise.master.extenstion

import org.bukkit.Location

fun Location.copy(): Location = Location(world, x, y, z, pitch, yaw)