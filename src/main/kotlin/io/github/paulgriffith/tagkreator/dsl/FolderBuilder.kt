package io.github.paulgriffith.tagkreator.dsl

import io.github.paulgriffith.tagkreator.model.Folder
import io.github.paulgriffith.tagkreator.model.UdtInstance

@TagDslMarker
class FolderBuilder : AbstractFolderBuilder() {
    @TagDslMarker
    operator fun String.invoke(block: FolderBuilder.() -> Unit) {
        tags += Folder(
            name = this,
            tags = FolderBuilder().apply(block).tags
        )
    }

    @TagDslMarker
    fun udtInstance(
        typeId: TypeId,
        name: String,
        overrides: UdtDefinitionBuilder.() -> Unit = {},
    ) {
        val override = UdtDefinitionBuilder().apply(overrides)
        tags += UdtInstance(
            name = name,
            typeId = typeId.id,
            parameters = override.params,
            tags = override.tags,
        )
    }

    @TagDslMarker
    fun TypeId.instance(
        name: String,
        overrides: UdtDefinitionBuilder.() -> Unit = {},
    ) {
        val override = UdtDefinitionBuilder().apply(overrides)
        tags += UdtInstance(
            name = name,
            typeId = this.id,
            parameters = override.params,
            tags = override.tags,
        )
    }
}
