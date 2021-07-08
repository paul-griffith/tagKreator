package io.github.paulgriffith.tagkreator

import io.github.paulgriffith.tagkreator.dsl.FolderBuilder
import io.github.paulgriffith.tagkreator.dsl.ProviderBuilder.Companion.provider
import io.github.paulgriffith.tagkreator.dsl.TypeId
import io.github.paulgriffith.tagkreator.json.TAG_JSON
import io.github.paulgriffith.tagkreator.model.AtomicTag.HistoricalDeadbandMode.Absolute
import io.github.paulgriffith.tagkreator.model.AtomicTag.HistoricalDeadbandStyle.Analog_Compressed
import io.github.paulgriffith.tagkreator.model.AtomicTag.Permissions.Type.AllOf
import io.github.paulgriffith.tagkreator.model.AtomicTag.Permissions.Type.AnyOf
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlin.time.Duration

fun main() {
    // Define a variable tags, which will be the result of calling the function `provider` - the 'root' of the Tag DSL
    val tags = provider {
        // If within IntelliJ, you'll notice `this` called out as `TagExportBuilder` within this scope.
        // That's the key to how the DSL works - increasingly nested scopes providing helper functions.
        // Diving down again, we open a new scope to construct our UDT definitions.
        types {
            // String literals open scopes, to act as folders
            "folder" {
                udt("compressor") {
                    parameters {
                        // Parameters can be numeric literals or strings
                        param("number", -1)
                    }
                    // `by` is Kotlin syntactic sugar for 'delegation' - this will throw an error at runtime if a key 
                    // 'number' does not exist in the parameter map, or if it's not an integer.
                    val number: Int by params
                    // Tags can be created within our UDT definition. Only tag type, data type, and name are required.
                    memory<Int>("abc") {
                        // The value must match the data type specified at the top level - any non-integer value will
                        // be a compile time error.
                        value {
                            123
                        }
                    }
                    // The core tag types (memory, opc, expression, query, etc) have dedicated builder functions
                    opc<Long>("opc") {
                        // Failing to provide required values, such as OPC item path or server, will throw an error
                        itemPath = "[device]Path.$number"
                        server = "Ignition OPC UA Server"
                    }
                }
            }
        }
        "folder" {
            // Because this is just a Kotlin source file, loops, variables, etc are all completely acceptable
            // Consider this a multi-instance wizard on steroids
            for (i in 1..10) {
                // TypeId is a special class used to indicate UDT type ids.
                val compressor = TypeId("folder/compressor")

                // Instances can be created with a factory function provided a TypeId
                udtInstance(typeId = compressor, name = "motor $i") {
                    // Any parameter values or tag definitions in this block are overrides
                    parameters {
                        param("number", i)
                    }
                }
                // Alternately, you can construct new instances from a TypeId instance
                // (as long as you're in the right scope)
                compressor.instance(name = "compressor $i")
            }
            "folder2" {
                // Code does not have to reside entirely within one nested scope - you can create higher order functions
                // to simplify repetitive logic easily
                createMemoryTag()
                // Some more exotic datatypes are supported, though Datasets are still pending
                memory<JsonObject>("document") {
                    value {
                        // buildJsonObject comes from a Kotlin library to allow directly building a JSON structure
                        buildJsonObject {
                            put("abc", 123)
                        }
                    }
                    alarms {
                        alarm("HIHI") {
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

private fun FolderBuilder.createMemoryTag() {
    memory<Long>(name = "longTag") {
        value {
            123
        }
        // Simple values are directly available as mutable properties
        tooltip = "Some tooltip"
        documentation = "Some documentation"
        // However, most values are broken into categories for easier configuration
        history {
            provider = "DB"
            enabled = false
            deadbandMode = Absolute
            deadbandStyle = Analog_Compressed
            maxAge = Duration.hours(2)
        }
        security {
            readOnly = false
            read(AnyOf) {
                levels {
                    level("Authenticated")
                }
            }
            write(AllOf) {
                levels {
                    level("Authenticated") {
                        level("Administrator")
                    }
                }
            }
        }
        // Scripts are just string literals
        scripts {
            valueChanged {
                """#doSomething"""
            }
            qualityChanged(enabled = false) { "" }
            alarmActive(enabled = false) { "" }
            alarmAcked(enabled = false) { "" }
            alarmCleared(enabled = false) { "" }
        }
        alarms {
            alarm(name = "HIHI") {
                // Alarm properties (that can be) can be raw values, or bound to tags or expressions:
                enabled = direct(true)
                label = expression("this is a label expression")
                priority = tag("[~]path/to/some/tag")
            }
        }
    }
}
