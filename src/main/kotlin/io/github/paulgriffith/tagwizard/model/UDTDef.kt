package io.github.paulgriffith.tagwizard.model

import io.github.paulgriffith.tagwizard.json.ColorSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonPrimitive
import java.awt.Color

@Serializable
@SerialName("UdtType")
data class UDTDef(
    override val name: String,
    val tags: List<Tag> = emptyList(),
    @SerialName("typeId")
    val parentTypeId: String? = "",
    val parameters: Map<String, Parameter> = emptyMap(),
    @Serializable(with = ColorSerializer::class) val typeColor: Color? = null,
) : Tag() {

    @Serializable
    data class Parameter(
        val value: JsonPrimitive,
        val dataType: Type,
    ) {
        enum class Type {
            Integer,
            Float,
            String;
        }
    }
}
