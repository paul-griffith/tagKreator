package io.github.paulgriffith.tagkreator.json

import io.github.paulgriffith.tagkreator.model.Alarm
import io.github.paulgriffith.tagkreator.model.Alarm.Bindable
import io.github.paulgriffith.tagkreator.model.Alarm.ExprBinding
import io.github.paulgriffith.tagkreator.model.Alarm.TagBinding
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.serializer
import kotlin.reflect.KType

abstract class BindableSerializer<T>(
    protected val type: KType,
) : KSerializer<Bindable<T>> {
    override val descriptor = buildClassSerialDescriptor("Bindable", serialDescriptor(type)) {
        element("value", serialDescriptor(type))
        element<String>("bindType", isOptional = true)
    }

    abstract fun JsonPrimitive.transform(): T

    override fun deserialize(decoder: Decoder): Bindable<T> {
        val input = decoder as JsonDecoder
        return when (val tree = input.decodeJsonElement()) {
            is JsonPrimitive -> Alarm.Direct(tree.transform())
            is JsonObject -> {
                val value = requireNotNull(tree.jsonObject["value"]?.jsonPrimitive?.content)
                when (val bindType = tree.jsonObject["bindType"]?.jsonPrimitive?.content) {
                    "Tag" -> TagBinding(value)
                    "Expression" -> ExprBinding(value)
                    else -> error("Unexpected value for 'bindType': $bindType")
                }
            }
            else -> error("Unexpected object type for bindable")
        }
    }

    override fun serialize(encoder: Encoder, value: Bindable<T>) {
        if (value is Alarm.Direct) {
            encoder.encodeSerializableValue(serializer(type), value.value)
        } else {
            encoder.beginStructure(descriptor).let { structure ->
                structure.encodeStringElement(
                    descriptor = descriptor,
                    index = 0,
                    value = when (value) {
                        is ExprBinding -> value.value
                        is TagBinding -> value.value
                        else -> error("Impossible")
                    }
                )
                structure.encodeStringElement(
                    descriptor = descriptor,
                    index = 1,
                    value = if (value is ExprBinding) "Expression" else "Tag"
                )
                structure.endStructure(descriptor)
            }
        }
    }
}
