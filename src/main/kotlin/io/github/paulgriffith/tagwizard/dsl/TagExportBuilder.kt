package io.github.paulgriffith.tagwizard.dsl

import io.github.paulgriffith.tagwizard.model.Folder
import io.github.paulgriffith.tagwizard.model.Tag
import io.github.paulgriffith.tagwizard.model.UDTDef
import io.github.paulgriffith.tagwizard.model.UDTInstance
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@TagDslMarker
class TagExportBuilder : AbstractFolderBuilder() {
    fun types(block: TypesBuilder.() -> Unit) {
        tags += Folder(
            name = "_types_",
            tags = TypesBuilder("").apply(block).tags
        )
    }

    operator fun String.invoke(block: FolderBuilder.() -> Unit) {
        tags += Folder(
            name = this,
            tags = FolderBuilder().apply(block).tags
        )
    }

    inner class UDT(
        private val ctor: UdtDefinitionBuilder.() -> Unit,
    ) : ReadOnlyProperty<Nothing?, TypeId> {
        override operator fun getValue(thisRef: Nothing?, property: KProperty<*>): TypeId {
            tags.filterIsInstance<UDTDef>()
                .find { it.name == property.name }
                ?: UdtDefinitionBuilder().apply(ctor).build(property.name).also(tags::plusAssign)
            return TypeId(property.name)
        }
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

    companion object {
        fun tags(block: TagExportBuilder.() -> Unit): List<Tag> {
            return TagExportBuilder().apply(block).tags
        }
    }
}
