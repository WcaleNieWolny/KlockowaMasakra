package pl.wolny.kwadratowamasakraskull.model

import kotlinx.serialization.Serializable

@Serializable
data class SkullPlayer(val haveSkull: Boolean, val skullTime: Long)
