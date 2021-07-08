package io.github.paulgriffith.tagkreator.json

import io.github.paulgriffith.tagkreator.model.Alarm
import kotlinx.serialization.json.JsonPrimitive
import kotlin.reflect.typeOf

@OptIn(ExperimentalStdlibApi::class)
object BindablePrioritySerializer : BindableSerializer<Alarm.Priority>(typeOf<Alarm.Priority>()) {
    override fun JsonPrimitive.transform() = content.let(Alarm.Priority::valueOf)
}
