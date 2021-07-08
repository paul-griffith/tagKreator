package io.github.paulgriffith.tagkreator.model

import io.github.paulgriffith.tagkreator.JsonMatchers.shouldBeJsonObject
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.FunSpec
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.put

@DisplayName("Atomic Tag Tests")
class AtomicTagTests : FunSpec({
    test("Memory") {
        AtomicTag(
            name = "memoryTag",
            valueSource = AtomicTag.ValueSource.Memory,
            value = JsonPrimitive(123)
        ).toJson() shouldBeJsonObject {
            put("tagType", "AtomicTag")
            // Default valueSource is memory for benefit of deserialization - would ideally be required
            // put("valueSource", "memory")
            put("name", "memoryTag")
            put("value", 123)
        }
    }

    test("Expression") {
        AtomicTag(
            name = "expressionTag",
            valueSource = AtomicTag.ValueSource.Expression,
            expression = "1 + 1",
        ).toJson() shouldBeJsonObject {
            put("tagType", "AtomicTag")
            put("valueSource", "expr")
            put("name", "expressionTag")
            put("expression", "1 + 1")
        }
    }

    test("OPC") {
        AtomicTag(
            name = "opcTag",
            valueSource = AtomicTag.ValueSource.OPC,
            opcItemPath = "path/to/opc",
            opcServer = "OPC_Server",
        ).toJson() shouldBeJsonObject {
            put("tagType", "AtomicTag")
            put("valueSource", "opc")
            put("name", "opcTag")
            put("opcItemPath", "path/to/opc")
            put("opcServer", "OPC_Server")
        }
    }
})
