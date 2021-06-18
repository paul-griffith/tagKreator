package io.github.paulgriffith.tagwizard.dsl

import io.github.paulgriffith.tagwizard.model.Folder
import io.github.paulgriffith.tagwizard.model.UDTInstance

@TagDslMarker
class FolderBuilder : AbstractFolderBuilder() {
    operator fun String.invoke(block: FolderBuilder.() -> Unit) {
        tags += Folder(
            name = this,
            tags = FolderBuilder().apply(block).tags
        )
    }

    fun udtInstance(typeId: TypeId, name: String, overrides: UdtDefinitionBuilder.() -> Unit = {}) {
        val override = UdtDefinitionBuilder().apply(overrides)
        tags += UDTInstance(
            name = name,
            typeId = typeId.id,
            parameters = override.params,
            tags = override.tags,
        )
    }
}
