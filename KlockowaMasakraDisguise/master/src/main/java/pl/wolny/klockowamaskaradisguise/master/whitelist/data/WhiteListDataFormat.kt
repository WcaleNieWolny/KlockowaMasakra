package pl.wolny.klockowamaskaradisguise.master.whitelist.data

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class WhiteListDataFormat(val mutableList: MutableList<String>, var enabled: Boolean = true)
