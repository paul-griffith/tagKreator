package io.github.paulgriffith.tagkreator.dsl

import io.github.paulgriffith.tagkreator.model.AtomicTag

class ExpressionTagBuilder<T>(
    override val name: String,
    override val type: AtomicTag.DataType,
) : TagBuilder<T>(AtomicTag.ValueSource.Expression) {
    lateinit var expression: String

    override fun createBaseInstance(): AtomicTag = super.createBaseInstance().copy(
        expression = expression
    )
}
