package io.github.paulgriffith.tagkreator.dsl

import io.github.paulgriffith.tagkreator.model.AtomicTag

@TagDslMarker
class SecurityBuilder {
    var readOnly: Boolean = false
    private var readPermissions: AtomicTag.Permissions? = null
    private var writePermissions: AtomicTag.Permissions? = null

    @TagDslMarker
    fun read(
        type: AtomicTag.Permissions.Type,
        block: PermissionsBuilder.() -> Unit,
    ) {
        readPermissions = PermissionsBuilder(type).apply(block).build()
    }

    @TagDslMarker
    fun write(
        type: AtomicTag.Permissions.Type,
        block: PermissionsBuilder.() -> Unit,
    ) {
        writePermissions = PermissionsBuilder(type).apply(block).build()
    }

    internal fun getConfig(): TagConfig = {
        copy(
            readOnly = this@SecurityBuilder.readOnly,
            readPermissions = this@SecurityBuilder.readPermissions,
            writePermissions = this@SecurityBuilder.writePermissions,
        )
    }
}
