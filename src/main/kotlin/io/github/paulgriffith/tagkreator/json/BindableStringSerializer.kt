package io.github.paulgriffith.tagkreator.json

import kotlinx.serialization.json.JsonPrimitive
import kotlin.reflect.typeOf

@OptIn(ExperimentalStdlibApi::class)
object BindableStringSerializer : BindableSerializer<String>(typeOf<String>()) {
    override fun JsonPrimitive.transform() = content
}
