package io.github.paulgriffith.tagkreator.gui.tree

import io.github.paulgriffith.tagkreator.gui.IconCache
import io.github.paulgriffith.tagkreator.model.AtomicTag

class ExpressionTagNode(tag: AtomicTag, parent: TagNode?) : TagNode(parent) {
    override val icon = IconCache.TAG
    override val children: List<Node> = listOf(
        node("Expression: ${tag.expression}"),
    )
    override val text = "Expression - ${tag.name}"
}
