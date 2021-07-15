package io.github.paulgriffith.tagkreator.gui.tree

import io.github.paulgriffith.tagkreator.gui.IconCache
import io.github.paulgriffith.tagkreator.model.AtomicTag

class MemoryTagNode(tag: AtomicTag, parent: TagNode?) : TagNode(parent) {
    override val icon = IconCache.TAG
    override val children: List<Node> = listOf(
        node("Value: ${tag.value}"),
    )
    override val text = "Memory - ${tag.name}"
}
