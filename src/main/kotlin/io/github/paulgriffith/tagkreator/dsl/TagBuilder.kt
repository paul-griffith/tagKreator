package io.github.paulgriffith.tagkreator.dsl

import io.github.paulgriffith.tagkreator.model.AtomicTag

@TagDslMarker
sealed class TagBuilder<T>(
    private val valueSource: AtomicTag.ValueSource,
) {
    abstract val name: String
    abstract val type: AtomicTag.DataType

    protected val mutations: MutableList<TagConfig> = mutableListOf()

    var tooltip: String? = null
    var documentation: String? = null

    @TagDslMarker
    fun security(block: SecurityBuilder.() -> Unit) {
        mutations += SecurityBuilder().apply(block).getConfig()
    }

    @TagDslMarker
    fun history(block: HistoryConfigBuilder.() -> Unit) {
        mutations += HistoryConfigBuilder().apply(block).getConfig()
    }

    @TagDslMarker
    fun scripts(block: EventScriptsBuilder.() -> Unit) {
        mutations += {
            copy(eventScripts = EventScriptsBuilder().apply(block).scripts)
        }
    }

    @TagDslMarker
    fun alarms(block: AlarmListBuilder.() -> Unit) {
        mutations += {
            copy(alarms = AlarmListBuilder().apply(block).alarms)
        }
    }

    protected open fun createBaseInstance(): AtomicTag = AtomicTag(
        name = name,
        valueSource = valueSource,
        dataType = type,
        tooltip = tooltip,
        documentation = documentation,
    )

    fun build(): AtomicTag {
        return mutations.fold(createBaseInstance()) { running, mutation ->
            mutation.invoke(running)
        }
    }
}
