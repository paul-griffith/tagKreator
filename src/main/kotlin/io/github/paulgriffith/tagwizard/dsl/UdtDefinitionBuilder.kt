package io.github.paulgriffith.tagwizard.dsl

import io.github.paulgriffith.tagwizard.model.Folder
import io.github.paulgriffith.tagwizard.model.UDTDef
import kotlinx.serialization.json.double
import kotlinx.serialization.json.float
import kotlinx.serialization.json.int
import kotlinx.serialization.json.long
import java.awt.Color
import kotlin.reflect.KProperty

@TagDslMarker
class UdtDefinitionBuilder : AbstractFolderBuilder() {
    var parentId: TypeId? = null
    var params: MutableMap<String, UDTDef.Parameter> = linkedMapOf()
    var typeColor: Color? = null

    fun parameters(block: ParametersBuilder.() -> Unit): List<UDTDef.Parameter> {
        params = ParametersBuilder().apply(block).parameters
        return params.values.toList()
    }

    inline operator fun <reified T> MutableMap<String, UDTDef.Parameter>.getValue(
        thisRef: Any?,
        property: KProperty<*>,
    ): T {
        val primitive = getValue(property.name).value
        return when (T::class) {
            Int::class -> primitive.int as T
            Long::class -> primitive.long as T
            Float::class -> primitive.float as T
            Double::class -> primitive.double as T
            String::class -> primitive.content as T
            else -> primitive.content as T
        }
    }

    operator fun String.invoke(block: FolderBuilder.() -> Unit) {
        tags += Folder(
            name = this,
            tags = FolderBuilder().apply(block).tags
        )
    }

    fun build(name: String): UDTDef {
        return UDTDef(
            name = name,
            tags = tags,
            parentTypeId = parentId?.id.orEmpty(),
            parameters = params,
            typeColor = typeColor,
        )
    }
}