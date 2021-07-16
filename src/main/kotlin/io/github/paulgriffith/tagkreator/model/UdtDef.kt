package io.github.paulgriffith.tagkreator.model

import io.github.paulgriffith.tagkreator.json.ColorSerializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonPrimitive
import java.awt.Color

@Serializable
@SerialName("UdtType")
data class UdtDef(
    override val name: String,
    override val tags: List<Tag> = emptyList(),
    @SerialName("typeId")
    val parentTypeId: String? = "",
    val parameters: Map<String, Parameter> = emptyMap(),
    @Serializable(with = ColorSerializer::class) val typeColor: Color? = null,
) : Tag(), TagContainer {

    @Serializable(with = ParameterSerializer::class)
    data class Parameter(
        val value: JsonPrimitive = JsonNull,
        val dataType: Type,
    ) {
        @Serializable
        enum class Type {
            Integer,
            Float,
            String;
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Serializer(Parameter::class)
    object ParameterSerializer : KSerializer<Parameter> {
        override val descriptor = buildClassSerialDescriptor("UdtDef.Parameter") {
            element<JsonPrimitive>("value")
            element<Parameter.Type>("dataType")
        }

        override fun deserialize(decoder: Decoder): Parameter {
            return when (val element = (decoder as JsonDecoder).decodeJsonElement()) {
                is JsonObject -> Parameter(
                    value = element["value"]!!.jsonPrimitive,
                    dataType = Parameter.Type.valueOf(element["dataType"]!!.jsonPrimitive.content)
                )
                else -> Parameter(
                    value = element.jsonPrimitive,
                    dataType = Parameter.Type.String, // TODO peek datatype from literal
                )
            }
        }

        override fun serialize(encoder: Encoder, value: Parameter) {
            val compositeEncoder = encoder.beginStructure(descriptor)
            compositeEncoder.encodeSerializableElement(descriptor, 0, JsonPrimitive.serializer(), value.value)
            compositeEncoder.encodeSerializableElement(descriptor, 1, Parameter.Type.serializer(), value.dataType)
            compositeEncoder.endStructure(descriptor)
        }
    }
}
