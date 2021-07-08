package io.github.paulgriffith.tagkreator.json

import io.github.paulgriffith.tagkreator.model.Alarm
import kotlinx.serialization.json.JsonPrimitive
import kotlin.reflect.typeOf

@OptIn(ExperimentalStdlibApi::class)
object BindableAckModeSerializer : BindableSerializer<Alarm.AckMode>(typeOf<Alarm.AckMode>()) {
    override fun JsonPrimitive.transform() = content.let(Alarm.AckMode::valueOf)
}
