package io.github.paulgriffith.tagkreator.dsl

import io.github.paulgriffith.tagkreator.model.Folder
import io.github.paulgriffith.tagkreator.model.Tag
import io.github.paulgriffith.tagkreator.model.UdtDef

@TagDslMarker
class TypesBuilder(private val path: String) {
    val tags: MutableList<Tag> = mutableListOf()

    @TagDslMarker
    operator fun String.invoke(block: TypesBuilder.() -> Unit) {
        tags += Folder(
            name = this,
            tags = TypesBuilder("$path/$this").apply(block).tags
        )
    }

    @TagDslMarker
    fun udt(name: String, block: UdtDefinitionBuilder.() -> Unit = {}): TypeId {
        val udt = UdtDefinitionBuilder().apply(block)
        tags += UdtDef(
            name = name,
            parameters = udt.params,
            tags = udt.tags,
        )
        return TypeId("$path/$name")
    }
}
