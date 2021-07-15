package io.github.paulgriffith.tagkreator.model

sealed interface TagContainer {
    val tags: List<Tag>?
}
