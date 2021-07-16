package io.github.paulgriffith.tagkreator.gui.tree

import java.util.Collections
import java.util.Enumeration
import javax.swing.Icon
import javax.swing.tree.TreeNode

sealed class Node : TreeNode {
    protected abstract val parent: Node?
    abstract val children: List<Node>

    abstract val text: String
    open val icon: Icon? = null

    override fun getChildAt(childIndex: Int): TreeNode? = children.getOrNull(childIndex)

    override fun getChildCount(): Int = children.size

    override fun getParent(): TreeNode? = parent

    override fun getIndex(node: TreeNode): Int = children.indexOf(node)

    override fun getAllowsChildren(): Boolean = true

    override fun isLeaf(): Boolean = children.isEmpty()

    override fun children(): Enumeration<out TreeNode> = Collections.enumeration(children)
}
