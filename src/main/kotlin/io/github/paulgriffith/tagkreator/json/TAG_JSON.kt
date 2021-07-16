package io.github.paulgriffith.tagkreator.json

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

val TAG_JSON = Json {
    prettyPrint = true
    encodeDefaults = false
    classDiscriminator = "tagType"
}

/**
 * A modified instance of [TAG_JSON] that only uses two spaces, to match Ignition's default export format for easy diffing.
 */
val EXPORT_JSON = Json(TAG_JSON) {
    @OptIn(ExperimentalSerializationApi::class)
    prettyPrintIndent = "  "
}
