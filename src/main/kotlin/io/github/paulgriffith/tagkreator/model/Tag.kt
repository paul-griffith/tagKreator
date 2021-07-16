package io.github.paulgriffith.tagkreator.model

import io.github.paulgriffith.tagkreator.json.TAG_JSON
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
sealed class Tag {
    abstract val name: String

    fun toJson(): JsonElement {
        return TAG_JSON.encodeToJsonElement(serializer(), this)
    }

    companion object {
        const val TYPES_FOLDER_NAME = "_types_"
    }
}
