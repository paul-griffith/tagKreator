package io.github.paulgriffith.tagwizard.dsl

import io.github.paulgriffith.tagwizard.model.AtomicTag

@TagDslMarker
class SecurityLevelsBuilder {
    val levels: MutableList<AtomicTag.Permissions.SecurityLevel> = mutableListOf()

    fun level(name: String, block: SecurityLevelsBuilder.() -> Unit = {}) {
        levels += AtomicTag.Permissions.SecurityLevel(
            name = name,
            children = SecurityLevelsBuilder().apply(block).levels
        )
    }
}
