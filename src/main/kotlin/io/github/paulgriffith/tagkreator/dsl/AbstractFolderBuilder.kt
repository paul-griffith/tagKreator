package io.github.paulgriffith.tagkreator.dsl

import io.github.paulgriffith.tagkreator.model.AtomicTag
import io.github.paulgriffith.tagkreator.model.Tag
import kotlinx.serialization.json.JsonObject

abstract class AbstractFolderBuilder {
    val tags: MutableList<Tag> = mutableListOf()

    inline fun <reified T> getDatatype(): AtomicTag.DataType {
        return when (T::class) {
            Boolean::class -> AtomicTag.DataType.Boolean
            BooleanArray::class -> AtomicTag.DataType.BooleanArray
            Byte::class -> AtomicTag.DataType.Int1
            ByteArray::class -> AtomicTag.DataType.Int1Array
            Short::class -> AtomicTag.DataType.Int2
            ShortArray::class -> AtomicTag.DataType.Int2Array
            Int::class -> AtomicTag.DataType.Int4
            IntArray::class -> AtomicTag.DataType.Int4Array
            Long::class -> AtomicTag.DataType.Int8
            LongArray::class -> AtomicTag.DataType.Int8Array
            Float::class -> AtomicTag.DataType.Float4
            FloatArray::class -> AtomicTag.DataType.Float4Array
            Double::class -> AtomicTag.DataType.Float8
            DoubleArray::class -> AtomicTag.DataType.Float8Array
            String::class, Char::class, CharArray::class -> AtomicTag.DataType.String
            JsonObject::class -> AtomicTag.DataType.Document
            // TODO Dataset?
            else -> AtomicTag.DataType.String
        }
    }

    @TagDslMarker
    inline fun <reified T> memory(
        name: String,
        block: MemoryTagBuilder<T>.() -> Unit = {},
    ) {
        val type = getDatatype<T>()
        tags += MemoryTagBuilder<T>(name, type).apply(block).build()
    }

    @TagDslMarker
    inline fun <reified T> expression(
        name: String,
        block: ExpressionTagBuilder<T>.() -> Unit = {},
    ) {
        val type = getDatatype<T>()
        tags += ExpressionTagBuilder<T>(name, type).apply(block).build()
    }

    @TagDslMarker
    inline fun <reified T> opc(
        name: String,
        block: OPCTagBuilder<T>.() -> Unit,
    ) {
        val type = getDatatype<T>()
        tags += OPCTagBuilder<T>(name, type).apply(block).build()
    }

    @TagDslMarker
    inline fun <reified T> query(
        name: String,
        block: QueryTagBuilder<T>.() -> Unit,
    ) {
        val type = getDatatype<T>()
        tags += QueryTagBuilder<T>(name, type).apply(block).build()
    }
}
