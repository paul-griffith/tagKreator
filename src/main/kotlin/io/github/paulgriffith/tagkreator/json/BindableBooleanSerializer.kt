package io.github.paulgriffith.tagkreator.json

import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.boolean
import kotlin.reflect.typeOf

@OptIn(ExperimentalStdlibApi::class)
object BindableBooleanSerializer : BindableSerializer<Boolean>(typeOf<Boolean>()) {
    override fun JsonPrimitive.transform() = boolean
}
