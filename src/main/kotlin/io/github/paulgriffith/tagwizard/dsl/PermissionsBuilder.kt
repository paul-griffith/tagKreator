package io.github.paulgriffith.tagwizard.dsl

import io.github.paulgriffith.tagwizard.model.AtomicTag

@TagDslMarker
class PermissionsBuilder {
    lateinit var type: AtomicTag.Permissions.Type

    private val levels: MutableList<AtomicTag.Permissions.SecurityLevel> = mutableListOf()

    fun levels(block: SecurityLevelsBuilder.() -> Unit) {
        levels += SecurityLevelsBuilder().apply(block).levels
    }

    internal fun build(): AtomicTag.Permissions = AtomicTag.Permissions(
        type = type,
        securityLevels = levels,
    )
}
