package io.github.paulgriffith.tagwizard.dsl

import io.github.paulgriffith.tagwizard.model.AtomicTag

@TagDslMarker
class MemoryTagBuilder<T>(
    override val name: String,
    override val type: AtomicTag.DataType,
) : TagBuilder<T>(AtomicTag.ValueSource.Memory) {
    fun value(block: () -> T?) {
        mutations += {
            copy(value = block.invoke().toJsonElement())
        }
    }
}
