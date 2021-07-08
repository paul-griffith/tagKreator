package io.github.paulgriffith.tagkreator.dsl

import io.github.paulgriffith.tagkreator.model.AtomicTag

@TagDslMarker
class SecurityLevelsBuilder {
    val levels: MutableList<AtomicTag.Permissions.SecurityLevel> = mutableListOf()

    @TagDslMarker
    fun level(name: String, block: SecurityLevelsBuilder.() -> Unit = {}) {
        levels += AtomicTag.Permissions.SecurityLevel(
            name = name,
            children = SecurityLevelsBuilder().apply(block).levels
        )
    }
}
