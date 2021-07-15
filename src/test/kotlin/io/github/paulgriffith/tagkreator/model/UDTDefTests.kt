package io.github.paulgriffith.tagkreator.model

import io.github.paulgriffith.tagkreator.JsonMatchers.shouldBeJsonObject
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.FunSpec
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.addJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonArray
import kotlinx.serialization.json.putJsonObject
import java.awt.Color

@DisplayName("UDT Definition Tests")
class UDTDefTests : FunSpec({
    test("Basic UDT definition") {
        UdtDef(
            name = "abc",
            parentTypeId = "parent",
            parameters = mapOf(
                "number" to UdtDef.Parameter(
                    value = JsonPrimitive(123),
                    dataType = UdtDef.Parameter.Type.Integer,
                ),
                "string" to UdtDef.Parameter(
                    value = JsonPrimitive("str"),
                    dataType = UdtDef.Parameter.Type.String,
                )
            ),
            tags = listOf(
                Folder(name = "innerFolder")
            ),
            typeColor = Color(255, 255, 0)
        ).toJson() shouldBeJsonObject {
            put("tagType", "UdtType")
            put("name", "abc")
            put("typeId", "parent")
            put("typeColor", -256)
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
