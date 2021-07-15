package io.github.paulgriffith.tagkreator.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("UdtInstance")
data class UdtInstance(
    override val name: String,
    val typeId: String,
    val parameters: Map<String, UdtDef.Parameter>? = null,
    override val tags: List<Tag>? = null,
) : Tag(), TagContainer
