package io.github.paulgriffith.tagwizard.model

import io.github.paulgriffith.tagwizard.JsonMatchers.shouldBeJsonObject
import io.kotest.core.spec.style.FunSpec
import kotlinx.serialization.json.addJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonArray

class FolderTests : FunSpec({
    // Empty folder should be skipped
    test("Folder with no tags") {
        Folder(
            name = "abc",
        ).toJson() shouldBeJsonObject {
            put("tagType", "Folder")
            put("name", "abc")
        }

        Folder(
            name = "abc",
            tags = arrayListOf(),
        ).toJson() shouldBeJsonObject {
            put("tagType", "Folder")
            put("name", "abc")
        }
    }

    test("Folder containing folders") {
        Folder(
            name = "parent",
            tags = listOf(
                Folder(name = "child"),
                Folder(name = "child2"),
            ),
        ).toJson() shouldBeJsonObject {
            put("tagType", "Folder")
            put("name", "parent")
            putJsonArray("tags") {
                addJsonObject {
                    put("tagType", "Folder")
                    put("name", "child")
                }
                addJsonObject {
                    put("tagType", "Folder")
                    put("name", "child2")
                }
            }
        }
    }
})
