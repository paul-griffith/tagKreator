package io.github.paulgriffith.tagkreator.dsl

import io.github.paulgriffith.tagkreator.model.AtomicTag.Permissions

@TagDslMarker
class PermissionsBuilder(var type: Permissions.Type) {
    private val levels: MutableList<Permissions.SecurityLevel> = mutableListOf()

    @TagDslMarker
    fun levels(block: SecurityLevelsBuilder.() -> Unit) {
        levels += SecurityLevelsBuilder().apply(block).levels
    }

    internal fun build(): Permissions = Permissions(
        type = type,
        securityLevels = levels,
    )
}
