package io.github.paulgriffith.tagkreator.gui.tree

import io.github.paulgriffith.tagkreator.gui.IconCache
import io.github.paulgriffith.tagkreator.model.AtomicTag

class OPCTagNode(tag: AtomicTag, parent: TagNode?) : TagNode(parent) {
    override val icon = IconCache.TAG
    override val children: List<Node> = listOf(
        node("OPC Server: ${tag.opcServer}"),
        node("OPC Path: ${tag.opcItemPath}"),
    )
    override val text = "OPC - ${tag.name}"
}
