package io.github.paulgriffith.tagkreator.model

import io.github.paulgriffith.tagkreator.json.ColorSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
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

    @Serializable
    data class Parameter(
        val value: JsonPrimitive = JsonNull,
        val dataType: Type,
    ) {
        enum class Type {
            Integer,
            Float,
            String;
        }
    }
}
