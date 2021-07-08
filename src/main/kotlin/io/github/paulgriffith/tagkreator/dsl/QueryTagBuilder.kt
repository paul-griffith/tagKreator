package io.github.paulgriffith.tagkreator.dsl

import io.github.paulgriffith.tagkreator.model.AtomicTag

@TagDslMarker
class QueryTagBuilder<T>(
    override val name: String,
    override val type: AtomicTag.DataType,
) : TagBuilder<T>(AtomicTag.ValueSource.Query) {
    lateinit var query: String
    var queryType: AtomicTag.QueryType = AtomicTag.QueryType.AutoDetect

    override fun createBaseInstance(): AtomicTag = super.createBaseInstance().copy(
        query = query,
        queryType = queryType,
    )
}
