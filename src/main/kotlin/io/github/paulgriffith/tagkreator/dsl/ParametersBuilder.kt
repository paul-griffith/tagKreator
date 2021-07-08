package io.github.paulgriffith.tagkreator.dsl

import io.github.paulgriffith.tagkreator.model.UDTDef.Parameter
import kotlinx.serialization.json.JsonPrimitive

class ParametersBuilder {
    val parameters: MutableMap<String, Parameter> = mutableMapOf()

    @TagDslMarker
    fun param(name: String, value: Int) {
        parameters[name] = Parameter(value = JsonPrimitive(value), dataType = Parameter.Type.Integer)
    }

    @TagDslMarker
    fun param(name: String, value: Long) {
        parameters[name] = Parameter(value = JsonPrimitive(value), dataType = Parameter.Type.Integer)
    }

    @TagDslMarker
    fun param(name: String, value: Float) {
        parameters[name] = Parameter(value = JsonPrimitive(value), dataType = Parameter.Type.Float)
    }

    @TagDslMarker
    fun param(name: String, value: Double) {
        parameters[name] = Parameter(value = JsonPrimitive(value), dataType = Parameter.Type.Float)
    }

    @TagDslMarker
    fun param(name: String, value: String) {
        parameters[name] = Parameter(value = JsonPrimitive(value), dataType = Parameter.Type.String)
    }
}
