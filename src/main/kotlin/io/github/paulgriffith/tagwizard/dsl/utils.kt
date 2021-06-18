package io.github.paulgriffith.tagwizard.dsl

import io.github.paulgriffith.tagwizard.model.AtomicTag
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject

internal fun Any?.toJsonElement(): JsonElement {
    return when (val value = this) {
        is Number -> JsonPrimitive(value)
        is Boolean -> JsonPrimitive(value)
        is String -> JsonPrimitive(value)
        is Array<*> -> buildJsonArray {
            for (v in value) {
                add(v.toJsonElement())
            }
        }
        is List<*> -> buildJsonArray {
            for (v in value) {
                add(v.toJsonElement())
            }
        }
        is Map<*, *> -> buildJsonObject {
            for ((k, v) in value) {
                put(k as String, v.toJsonElement())
            }
        }
        is JsonElement -> value
        // TODO dataset?
        null -> JsonNull
        else -> throw IllegalArgumentException("Unexpected datatype $value")
    }
}

typealias TagConfig = AtomicTag.() -> AtomicTag
