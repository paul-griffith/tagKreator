package io.github.paulgriffith.tagkreator.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("UdtInstance")
data class UDTInstance(
    override val name: String,
    val typeId: String,
    val parameters: Map<String, UDTDef.Parameter>? = null,
    val tags: List<Tag>? = null,
) : Tag()
