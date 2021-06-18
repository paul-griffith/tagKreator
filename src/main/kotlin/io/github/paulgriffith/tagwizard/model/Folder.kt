package io.github.paulgriffith.tagwizard.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Folder")
data class Folder(
    override val name: String,
    val tags: List<Tag> = emptyList(),
) : Tag()
