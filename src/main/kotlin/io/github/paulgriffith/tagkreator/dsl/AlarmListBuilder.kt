package io.github.paulgriffith.tagkreator.dsl

import io.github.paulgriffith.tagkreator.model.Alarm

@TagDslMarker
class AlarmListBuilder {
    val alarms = mutableListOf<Alarm>()

    @TagDslMarker
    fun alarm(name: String, block: AlarmBuilder.() -> Unit) {
        alarms += AlarmBuilder(name = name).apply(block).build()
    }
}
