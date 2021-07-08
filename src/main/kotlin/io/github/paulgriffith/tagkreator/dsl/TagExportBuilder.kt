package io.github.paulgriffith.tagkreator.dsl

import io.github.paulgriffith.tagkreator.model.Folder
import io.github.paulgriffith.tagkreator.model.Tag
import io.github.paulgriffith.tagkreator.model.UDTInstance

@TagDslMarker
class TagExportBuilder : AbstractFolderBuilder() {
    @TagDslMarker
    fun types(block: TypesBuilder.() -> Unit) {
        tags += Folder(
            name = "_types_",
            tags = TypesBuilder("").apply(block).tags
        )
    }

    @TagDslMarker
    operator fun String.invoke(block: FolderBuilder.() -> Unit) {
        tags += Folder(
            name = this,
            tags = FolderBuilder().apply(block).tags
        )
    }

//    inner class UDT(
//        private val ctor: UdtDefinitionBuilder.() -> Unit,
//    ) : ReadOnlyProperty<Nothing?, TypeId> {
//        override operator fun getValue(thisRef: Nothing?, property: KProperty<*>): TypeId {
//            tags.filterIsInstance<UDTDef>()
//                .find { it.name == property.name }
//                ?: UdtDefinitionBuilder().apply(ctor).build(property.name).also(tags::plusAssign)
//            return TypeId(property.name)
//        }
//    }

    @TagDslMarker
    fun udtInstance(typeId: TypeId, name: String, overrides: UdtDefinitionBuilder.() -> Unit = {}) {
        val override = UdtDefinitionBuilder().apply(overrides)
        tags += UDTInstance(
            name = name,
            typeId = typeId.id,
            parameters = override.params,
            tags = override.tags,
        )
    }

    companion object {
        @TagDslMarker
        fun tags(block: TagExportBuilder.() -> Unit): List<Tag> {
            return TagExportBuilder().apply(block).tags
        }
    }
}
