package io.github.paulgriffith.tagwizard.dsl

import io.github.paulgriffith.tagwizard.model.AtomicTag

@TagDslMarker
class EventScriptsBuilder {
    val scripts: MutableSet<AtomicTag.EventScript> = mutableSetOf()

    fun valueChanged(enabled: Boolean = true, block: () -> String) {
        scripts += AtomicTag.EventScript(
            eventId = AtomicTag.EventScript.EventId.VALUE_CHANGED,
            script = block.invoke(),
            enabled = enabled,
        )
    }

    fun qualityChanged(enabled: Boolean = true, block: () -> String) {
        scripts += AtomicTag.EventScript(
            eventId = AtomicTag.EventScript.EventId.QUALITY_CHANGED,
            script = block.invoke(),
            enabled = enabled,
        )
    }

    fun alarmActive(enabled: Boolean = true, block: () -> String) {
        scripts += AtomicTag.EventScript(
            eventId = AtomicTag.EventScript.EventId.ALARM_ACTIVE,
            script = block.invoke(),
            enabled = enabled,
        )
    }

    fun alarmCleared(enabled: Boolean = true, block: () -> String) {
        scripts += AtomicTag.EventScript(
            eventId = AtomicTag.EventScript.EventId.ALARM_CLEARED,
            script = block.invoke(),
            enabled = enabled,
        )
    }

    fun alarmAcked(enabled: Boolean = true, block: () -> String) {
        scripts += AtomicTag.EventScript(
            eventId = AtomicTag.EventScript.EventId.ALARM_ACKED,
            script = block.invoke(),
            enabled = enabled,
        )
    }
}
