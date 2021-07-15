package io.github.paulgriffith.tagkreator.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Provider")
data class Provider(
    override val name: String,
    override val tags: List<Tag> = emptyList()
) : Tag(), TagContainer
