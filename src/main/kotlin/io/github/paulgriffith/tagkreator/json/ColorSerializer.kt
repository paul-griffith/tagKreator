package io.github.paulgriffith.tagkreator.json

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.awt.Color

object ColorSerializer : KSerializer<Color> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("java.awt.Color", PrimitiveKind.INT)

    override fun deserialize(decoder: Decoder) = Color(decoder.decodeInt())

    override fun serialize(encoder: Encoder, value: Color) = encoder.encodeInt(value.rgb)
}
