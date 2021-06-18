package io.github.paulgriffith.tagwizard.dsl

import io.github.paulgriffith.tagwizard.model.UDTDef
import kotlinx.serialization.json.JsonPrimitive

@TagDslMarker
class ParametersBuilder {
    val parameters: MutableMap<String, UDTDef.Parameter> = mutableMapOf()

    inline fun <reified T> param(name: String, value: T) {
        parameters[name] = when (T::class) {
            Int::class, Long::class -> UDTDef.Parameter(
                JsonPrimitive(value as Number),
                dataType = UDTDef.Parameter.Type.Integer
            )
            Float::class, Double::class -> UDTDef.Parameter(
                JsonPrimitive(value as Number),
                dataType = UDTDef.Parameter.Type.Float
            )
            else -> UDTDef.Parameter(
                JsonPrimitive(value.toString()),
                dataType = UDTDef.Parameter.Type.String
            )
        }
    }
}
