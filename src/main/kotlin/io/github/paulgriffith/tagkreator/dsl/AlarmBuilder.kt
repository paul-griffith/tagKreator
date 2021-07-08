package io.github.paulgriffith.tagkreator.dsl

import io.github.paulgriffith.tagkreator.model.Alarm
import io.github.paulgriffith.tagkreator.model.Alarm.Bindable
import io.github.paulgriffith.tagkreator.model.Alarm.Direct
import io.github.paulgriffith.tagkreator.model.Alarm.ExprBinding
import io.github.paulgriffith.tagkreator.model.Alarm.TagBinding

@TagDslMarker
class AlarmBuilder(private val name: String) {
    var enabled: Bindable<Boolean> = Direct(true)
    var priority: Bindable<Alarm.Priority> = Direct(Alarm.Priority.Low)
    var timestampSource: Alarm.TimestampSource = Alarm.TimestampSource.System
    var label: Bindable<String>? = null

    fun <T> tag(path: String): TagBinding<T> {
        return TagBinding(path)
    }

    fun <T> expression(value: String): ExprBinding<T> {
        return ExprBinding(value)
    }

    fun <T> direct(value: T): Direct<T> {
        return Direct(value)
    }

    fun build(): Alarm {
        return Alarm(
            name = name,
            enabled = enabled,
            priority = priority,
            timestampSource = timestampSource,
            label = label,
        )
    }

    // TODO: Bring in the rest of the alarm properties
}
