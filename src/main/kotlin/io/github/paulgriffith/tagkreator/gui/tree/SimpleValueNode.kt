package io.github.paulgriffith.tagkreator.gui.tree

import javax.swing.Icon

class SimpleValueNode(
    override val text: String,
    override val icon: Icon? = null,
    override val parent: TagNode,
) : Node() {
    override val children: List<Node> = emptyList()
}
