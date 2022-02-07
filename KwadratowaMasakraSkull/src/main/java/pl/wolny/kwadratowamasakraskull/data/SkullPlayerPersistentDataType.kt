package pl.wolny.kwadratowamasakraskull.data

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataType
import pl.wolny.kwadratowamasakraskull.model.SkullPlayer

@ExperimentalSerializationApi
class SkullPlayerPersistentDataType: PersistentDataType<ByteArray, SkullPlayer> {

    companion object{
        private val it = SkullPlayerPersistentDataType()
        fun get(): SkullPlayerPersistentDataType = it
    }

    override fun getPrimitiveType(): Class<ByteArray> {
        return  ByteArray::class.java
    }

    override fun getComplexType(): Class<SkullPlayer> {
        return SkullPlayer::class.java
    }

    override fun toPrimitive(complex: SkullPlayer, context: PersistentDataAdapterContext): ByteArray {
        return ProtoBuf { }.encodeToByteArray(SkullPlayer.serializer(), complex)
    }

    override fun fromPrimitive(primitive: ByteArray, context: PersistentDataAdapterContext): SkullPlayer {
        return ProtoBuf { }.decodeFromByteArray(primitive)
    }
}