package io.github.paulgriffith.tagwizard.dsl

import io.github.paulgriffith.tagwizard.model.Folder
import io.github.paulgriffith.tagwizard.model.UDTDef

@TagDslMarker
class TypesBuilder(private val path: String) : AbstractFolderBuilder() {
    operator fun String.invoke(block: TypesBuilder.() -> Unit) {
        tags += Folder(
            name = this,
            tags = TypesBuilder("$path/$this").apply(block).tags
        )
    }

    fun udt(name: String, block: UdtDefinitionBuilder.() -> Unit = {}): TypeId {
        val udt = UdtDefinitionBuilder().apply(block)
        tags += UDTDef(
            name = name,
            parameters = udt.params,
            tags = udt.tags,
        )
        return TypeId("$path/$name")
    }
}
