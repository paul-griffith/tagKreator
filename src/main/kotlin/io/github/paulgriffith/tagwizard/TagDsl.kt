package io.github.paulgriffith.tagwizard

import io.github.paulgriffith.tagwizard.dsl.FolderBuilder
import io.github.paulgriffith.tagwizard.dsl.TagExportBuilder.Companion.tags
import io.github.paulgriffith.tagwizard.dsl.TypeId
import io.github.paulgriffith.tagwizard.json.TAG_JSON
import io.github.paulgriffith.tagwizard.model.AtomicTag
import io.github.paulgriffith.tagwizard.model.AtomicTag.Permissions
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.int
import kotlinx.serialization.json.put
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun main() {
    val tags = tags {
        val motor: TypeId by UDT {
            val (number) = parameters {
                param("number", -1)
            }
            memory<Int>("abc")
            opc<Long>("opc") {
                itemPath = "[device]Path.${number.value.int}"
                server = "Ignition OPC UA Server"
            }
        }
        "folder" {
            for (i in 1..10) {
                udtInstance(motor, "motor $i") {
                    parameters {
                        param("number", i)
                    }
                }
            }
            "folder2" {
                createMemoryTag()
                memory<JsonObject>("document") {
                    value {
                        buildJsonObject {
                            put("abc", 123)
                        }
                    }
                }
                expression<String>("name") {
                    expression = """
                        "abc" + 123
                    """.trimIndent()
                }
            }
        }
    }

    println(TAG_JSON.encodeToString(tags))
}

@ExperimentalTime
private fun FolderBuilder.createMemoryTag() {
    memory<Long>("long") {
        value {
            123
        }
        history {
            provider = "DB"
            enabled = false
            deadbandMode = AtomicTag.HistoricalDeadbandMode.Absolute
            maxAge = Duration.hours(2)
        }
        security {
            read {
                type = Permissions.Type.AnyOf
                levels {
                    level("Authenticated") {
                        level("Roles")
                    }
                }
            }
        }
        scripts {
            valueChanged {
                """#doSomething"""
            }
        }
    }
}
