package io.github.paulgriffith.tagwizard.dsl

import io.github.paulgriffith.tagwizard.model.AtomicTag

@TagDslMarker
class QueryTagBuilder<T>(
    override val name: String,
    override val type: AtomicTag.DataType,
) : TagBuilder<T>(AtomicTag.ValueSource.QUERY) {
    lateinit var query: String
    var queryType: AtomicTag.QueryType = AtomicTag.QueryType.AutoDetect

    override fun createBaseInstance(): AtomicTag = super.createBaseInstance().copy(
        query = query,
        queryType = queryType,
    )
}
