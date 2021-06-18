package io.github.paulgriffith.tagwizard.model

import io.github.paulgriffith.tagwizard.json.TAG_JSON
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
sealed class Tag {
    abstract val name: String
}

fun Tag.toJson(): JsonElement {
    return TAG_JSON.encodeToJsonElement(Tag.serializer(), this)
}
