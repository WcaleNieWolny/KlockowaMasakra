package pl.wolny.klockowamasakralifes.ban

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class PlayerBan(
    @Serializable(with = UuidAsStringSerializer::class) val uuid: UUID,
    val time: Long
)
