package io.github.paulgriffith.tagwizard.dsl

import io.github.paulgriffith.tagwizard.model.AtomicTag

@TagDslMarker
class ExpressionTagBuilder<T>(
    override val name: String,
    override val type: AtomicTag.DataType,
) : TagBuilder<T>(AtomicTag.ValueSource.Expression) {
    lateinit var expression: String

    override fun createBaseInstance(): AtomicTag = super.createBaseInstance().copy(
        expression = expression
    )
}
