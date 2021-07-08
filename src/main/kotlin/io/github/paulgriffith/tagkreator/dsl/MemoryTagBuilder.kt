package io.github.paulgriffith.tagkreator.dsl

import io.github.paulgriffith.tagkreator.model.AtomicTag

class MemoryTagBuilder<T>(
    override val name: String,
    override val type: AtomicTag.DataType,
) : TagBuilder<T>(AtomicTag.ValueSource.Memory) {
    @TagDslMarker
    fun value(block: () -> T?) {
        mutations += {
            copy(value = block.invoke().toJsonElement())
        }
    }
}
