package io.github.paulgriffith.tagkreator.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Folder")
data class Folder(
    override val name: String,
    override val tags: List<Tag> = emptyList(),
) : Tag(), TagContainer
