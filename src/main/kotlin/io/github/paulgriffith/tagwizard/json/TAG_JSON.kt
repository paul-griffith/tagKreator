package io.github.paulgriffith.tagwizard.json

import kotlinx.serialization.json.Json

val TAG_JSON = Json {
    prettyPrint = true
    encodeDefaults = false
    classDiscriminator = "tagType"
}
