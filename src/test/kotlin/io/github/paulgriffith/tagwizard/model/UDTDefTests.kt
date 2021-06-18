package io.github.paulgriffith.tagwizard.model

import io.github.paulgriffith.tagwizard.JsonMatchers.shouldBeJsonObject
import io.kotest.core.spec.style.FunSpec
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.addJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonArray
import kotlinx.serialization.json.putJsonObject

class UDTDefTests : FunSpec({
    test("Basic UDT definition") {
        UDTDef(
            name = "abc",
            parentTypeId = "parent",
            parameters = mapOf(
                "number" to UDTDef.Parameter(
                    value = JsonPrimitive(123),
                    dataType = UDTDef.Parameter.Type.Integer,
                ),
                "string" to UDTDef.Parameter(
                    value = JsonPrimitive("str"),
                    dataType = UDTDef.Parameter.Type.String,
                )
            ),
            tags = listOf(
                Folder(name = "innerFolder")
            )
        ).toJson() shouldBeJsonObject {
            put("tagType", "UdtType")
            put("name", "abc")
            put("typeId", "parent")
            putJsonObject("parameters") {
                putJsonObject("number") {
                    put("value", 123)
                    put("dataType", "Integer")
                }
                putJsonObject("string") {
                    put("value", "str")
                    put("dataType", "String")
                }
            }
            putJsonArray("tags") {
                addJsonObject {
                    put("tagType", "Folder")
                    put("name", "innerFolder")
                }
            }
        }
    }
})
