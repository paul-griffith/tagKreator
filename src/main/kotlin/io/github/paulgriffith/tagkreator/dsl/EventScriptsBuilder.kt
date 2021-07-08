package io.github.paulgriffith.tagkreator.dsl

import io.github.paulgriffith.tagkreator.model.AtomicTag

@TagDslMarker
class EventScriptsBuilder {
    val scripts: MutableSet<AtomicTag.EventScript> = mutableSetOf()

    @TagDslMarker
    fun valueChanged(enabled: Boolean = true, block: () -> String) {
        scripts += AtomicTag.EventScript(
            eventId = AtomicTag.EventScript.EventId.VALUE_CHANGED,
            script = block.invoke(),
            enabled = enabled,
        )
    }

    @TagDslMarker
    fun qualityChanged(enabled: Boolean = true, block: () -> String) {
        scripts += AtomicTag.EventScript(
            eventId = AtomicTag.EventScript.EventId.QUALITY_CHANGED,
            script = block.invoke(),
            enabled = enabled,
        )
    }

    @TagDslMarker
    fun alarmActive(enabled: Boolean = true, block: () -> String) {
        scripts += AtomicTag.EventScript(
            eventId = AtomicTag.EventScript.EventId.ALARM_ACTIVE,
            script = block.invoke(),
            enabled = enabled,
        )
    }

    @TagDslMarker
    fun alarmCleared(enabled: Boolean = true, block: () -> String) {
        scripts += AtomicTag.EventScript(
            eventId = AtomicTag.EventScript.EventId.ALARM_CLEARED,
            script = block.invoke(),
            enabled = enabled,
        )
    }

    @TagDslMarker
    fun alarmAcked(enabled: Boolean = true, block: () -> String) {
        scripts += AtomicTag.EventScript(
            eventId = AtomicTag.EventScript.EventId.ALARM_ACKED,
            script = block.invoke(),
            enabled = enabled,
        )
    }
}
