package io.github.paulgriffith.tagwizard.dsl

import io.github.paulgriffith.tagwizard.model.AtomicTag

@TagDslMarker
sealed class TagBuilder<T>(
    private val valueSource: AtomicTag.ValueSource,
) {
    abstract val name: String
    abstract val type: AtomicTag.DataType

    protected val mutations: MutableList<TagConfig> = mutableListOf()

    var tooltip: String? = null
    var documentation: String? = null

    fun security(block: SecurityBuilder.() -> Unit) {
        mutations += SecurityBuilder().apply(block).config
    }

    fun history(block: HistoryConfigBuilder.() -> Unit) {
        mutations += HistoryConfigBuilder().apply(block).config
    }

    fun scripts(block: EventScriptsBuilder.() -> Unit) {
        mutations += {
            copy(eventScripts = EventScriptsBuilder().apply(block).scripts)
        }
    }

    protected open fun createBaseInstance(): AtomicTag = AtomicTag(
        name = name,
        valueSource = valueSource,
        dataType = type,
    )

    fun build(): AtomicTag {
        return mutations.fold(createBaseInstance()) { running, mutation ->
            mutation.invoke(running)
        }
    }
}
