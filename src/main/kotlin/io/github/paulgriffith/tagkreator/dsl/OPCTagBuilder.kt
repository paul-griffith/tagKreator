package io.github.paulgriffith.tagkreator.dsl

import io.github.paulgriffith.tagkreator.model.AtomicTag

class OPCTagBuilder<T>(
    override val name: String,
    override val type: AtomicTag.DataType,
) : TagBuilder<T>(AtomicTag.ValueSource.OPC) {
    lateinit var itemPath: String
    lateinit var server: String

    override fun createBaseInstance(): AtomicTag = super.createBaseInstance().copy(
        opcItemPath = itemPath,
        opcServer = server,
    )
}
