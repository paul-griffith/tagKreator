package io.github.paulgriffith.tagwizard

import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult
import io.kotest.matchers.should
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArrayBuilder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObjectBuilder
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject

object JsonMatchers {
    private val json = Json {
        prettyPrint = true
    }

    fun matchJson(expected: JsonElement) = object : Matcher<JsonElement?> {
        override fun test(value: JsonElement?): MatcherResult {
            return MatcherResult(
                passed = expected == value,
                failureMessage = "expected: ${json.encodeToString(expected)} but was ${json.encodeToString(value)}",
                negatedFailureMessage = "expected not to match with ${json.encodeToString(expected)} but matched: ${json.encodeToString(value)}"
            )
        }
    }

    inline infix fun JsonElement?.shouldBeJsonObject(obj: JsonObjectBuilder.() -> Unit) {
        this should matchJson(buildJsonObject(obj))
    }

    inline infix fun JsonElement?.shouldBeJsonArray(array: JsonArrayBuilder.() -> Unit) {
        this should matchJson(buildJsonArray(array))
    }
}
