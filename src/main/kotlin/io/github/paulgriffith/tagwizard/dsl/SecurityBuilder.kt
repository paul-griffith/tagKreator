package io.github.paulgriffith.tagwizard.dsl

import io.github.paulgriffith.tagwizard.model.AtomicTag

@TagDslMarker
class SecurityBuilder {
    var readOnly: Boolean = false
    private var readPermissions: AtomicTag.Permissions? = null
    private var writePermissions: AtomicTag.Permissions? = null

    fun read(block: PermissionsBuilder.() -> Unit) {
        readPermissions = PermissionsBuilder().apply(block).build()
    }

    fun write(block: PermissionsBuilder.() -> Unit) {
        writePermissions = PermissionsBuilder().apply(block).build()
    }

    val config: TagConfig
        get() = {
            copy(
                readOnly = readOnly,
                readPermissions = readPermissions,
                writePermissions = writePermissions,
            )
        }
}
