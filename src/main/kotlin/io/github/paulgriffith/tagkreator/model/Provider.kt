package io.github.paulgriffith.tagkreator.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Provider")
data class Provider(
    val name: String,
    val tags: List<Tag> = emptyList()
)
