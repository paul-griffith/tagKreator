package io.github.paulgriffith.tagkreator.dsl

import io.github.paulgriffith.tagkreator.model.AtomicTag
import io.github.paulgriffith.tagkreator.model.AtomicTag.Permissions
import io.kotest.assertions.asClue
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe

@DisplayName("Security Builder Tests")
class SecurityBuilderTests : FunSpec({
    fun configure(block: SecurityBuilder.() -> Unit): AtomicTag {
        val config = SecurityBuilder().apply(block).getConfig()
        return AtomicTag(name = "test").let(config)
    }

    test("Read permissions") {
        configure {
            read(Permissions.Type.AllOf) {
                levels {
                    level("Authenticated")
                }
            }
        }.readPermissions.shouldNotBeNull().asClue { permissions ->
            permissions.type shouldBe Permissions.Type.AllOf
            permissions.securityLevels.shouldHaveSize(1).first().asClue { levels ->
                levels.name shouldBe "Authenticated"
                levels.children.shouldBeEmpty()
            }
        }
    }

    test("Write permissions") {
        configure {
            write(Permissions.Type.AnyOf) {
                levels {
                    level("Administrator")
                }
            }
        }.writePermissions.shouldNotBeNull().asClue { permissions ->
            permissions.type shouldBe Permissions.Type.AnyOf
            permissions.securityLevels.shouldHaveSize(1).first().asClue { levels ->
                levels.name shouldBe "Administrator"
                levels.children.shouldBeEmpty()
            }
        }
    }

    test("Read only") {
        configure {
            readOnly = true
        }.asClue { tag ->
            tag.readOnly shouldBe true
        }
    }
})
