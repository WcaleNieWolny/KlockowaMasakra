package pl.wolny.klockowamaskaradisguise.master.utils

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.Mob
import pl.wolny.klockowamaskaradisguise.api.MobUtils
import java.lang.reflect.Method
import java.util.logging.Level

object MinecraftUtils: MobUtils {

 //   private val goalSelectorClass = Class.forName("net.minecraft.world.entity.ai.goal.PathfinderGoalSelector")
 //   private val mobClass = Class.forName("net.minecraft.world.entity.EntityInsentient")
    private val entityClass = Class.forName("net.minecraft.world.entity.Entity")
//    private val worldClass = Class.forName("net.minecraft.world.level.World")
//    private val paperWorldConfigClass = Class.forName("com.destroystokyo.paper.PaperWorldConfig")
//    private val paperConfigClass = Class.forName("com.destroystokyo.paper.PaperConfig")

//    private val clearGoalsMethod = goalSelectorClass.getDeclaredMethod("a")
    private val moveMethod = entityClass.getDeclaredMethod("setPositionRotation", Double::class.java, Double::class.java, Double::class.java, Float::class.java, Float::class.java)
//    private val saveWorldConfigurationMethod = paperConfigClass.getDeclaredMethod("saveConfig")
    private var getMobHandleMethod: Method? = null
//    private var getWorldHandleMethod: Method? = null

//    private val goalSelectorField = mobClass.declaredFields.filter {
//        it.type.equals(goalSelectorClass)
//    }[0]
//
//    private val paperConfigurationField = worldClass.declaredFields.filter {
//        it.type.equals(paperWorldConfigClass)
//    }[0]
//
//    private val maxCollisionsPerEntityField = paperWorldConfigClass.getDeclaredField("maxCollisionsPerEntity")

//    override fun removeMobGoals(mob: Mob) {
//        if(getMobHandleMethod == null){
//            getMobHandleMethod = mob::class.java.getDeclaredMethod("getHandle")
//        }
//        val realMob = getMobHandleMethod?.invoke(mob)
//        val goalSelector = goalSelectorField.get(realMob)
//        clearGoalsMethod.invoke(goalSelector)
//    }

    override fun moveEntity(entity: Entity, location: Location){
        if(getMobHandleMethod == null){
            getMobHandleMethod = entity::class.java.getDeclaredMethod("getHandle")
        }
        val realMob = getMobHandleMethod?.invoke(entity)
        moveMethod.invoke(realMob, location.x, location.y, location.z, location.yaw, location.pitch)
    }

//    fun setupRuntime(){
//        var resetRequired = false
//        Bukkit.getWorlds().forEach{
//            if(getWorldHandleMethod == null){
//                getWorldHandleMethod = it::class.java.getDeclaredMethod("getHandle")
//            }
//
//            val handle = getWorldHandleMethod!!.invoke(it)
//            val worldConfiguration = paperConfigurationField.get(handle)
//            val maxCollisionsPerEntity: Int = maxCollisionsPerEntityField.get(worldConfiguration) as Int
//
//            if(maxCollisionsPerEntity != -1){
//                if(!resetRequired){
//                    resetRequired = true
//                }
//
//                saveWorldConfigurationMethod.trySetAccessible()
//
//                maxCollisionsPerEntityField.set(worldConfiguration, -1)
//                saveWorldConfigurationMethod.invoke(worldConfiguration)
//            }
//        }
//
//        if(resetRequired){
//            Bukkit.getLogger().log(Level.SEVERE, "CRITICAL DISGUISE PROBLEM DETECTED!!! SETTING UP VALUE TEMPORARILY!")
//            Bukkit.getLogger().log(Level.SEVERE, """CHANGE "maxCollisionsPerEntity!!!" AS SOON AS POSSIBLE RESTARTING SERVER!""")
//        }
//    }
}