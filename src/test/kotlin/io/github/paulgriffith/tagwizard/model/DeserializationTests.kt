package io.github.paulgriffith.tagwizard.model

import io.github.paulgriffith.tagwizard.json.TAG_JSON
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import java.io.InputStreamReader

class DeserializationTests : FunSpec() {
    private val json = Json {
        prettyPrint = true
    }

    private inline fun <reified T> assertDeserializes(resourcePath: String) {
        val stream = this::class.java.getResourceAsStream("/io/github/paulgriffith/tagwizard/model/$resourcePath.json")
        stream.shouldNotBeNull()
        val text = stream.use { resource ->
            resource.reader().use(InputStreamReader::readText)
        }
        // Make sure we can deserialize the given .json into a model type
        shouldNotThrowAny {
            TAG_JSON.decodeFromString<T>(text)
                .shouldNotBeNull()
        }

        val decoded = TAG_JSON.decodeFromString(JsonElement.serializer(), text)
        val expected = json.decodeFromString(JsonElement.serializer(), text)
        decoded shouldBe expected
    }

    init {
        test("UDT Deserialization") {
            assertDeserializes<Tag>("parentAndChild")
        }

        test("History") {
            assertDeserializes<Tag>("history")
        }
    }
}
