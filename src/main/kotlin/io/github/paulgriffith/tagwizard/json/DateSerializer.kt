package io.github.paulgriffith.tagwizard.json

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.Date

object DateSerializer : KSerializer<Date> {
    override val descriptor = PrimitiveSerialDescriptor("java.util.Date", PrimitiveKind.LONG)

    override fun deserialize(decoder: Decoder) = Date(decoder.decodeLong())

    override fun serialize(encoder: Encoder, value: Date) = encoder.encodeLong(value.time)
}
