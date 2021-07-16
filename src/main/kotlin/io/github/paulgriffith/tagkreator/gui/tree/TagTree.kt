package io.github.paulgriffith.tagkreator.gui.tree

import com.formdev.flatlaf.extras.components.FlatTree
import io.github.paulgriffith.tagkreator.gui.IconCache
import io.github.paulgriffith.tagkreator.gui.action
import io.github.paulgriffith.tagkreator.gui.tree.TagNode.Companion.buildTreeNode
import io.github.paulgriffith.tagkreator.model.Tag
import java.awt.BorderLayout
import java.awt.Component
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JToolBar
import javax.swing.JTree
import javax.swing.ListSelectionModel
import javax.swing.tree.DefaultTreeCellRenderer
import javax.swing.tree.DefaultTreeModel
import javax.swing.tree.TreePath

class TagTree : JPanel(BorderLayout()) {
    val tree = FlatTree().apply {
        showsRootHandles = true
        model = null
        selectionModel.selectionMode = ListSelectionModel.SINGLE_SELECTION
        cellRenderer = object : DefaultTreeCellRenderer() {
            override fun getTreeCellRendererComponent(
                tree: JTree?,
                value: Any?,
                sel: Boolean,
                expanded: Boolean,
                leaf: Boolean,
                row: Int,
                hasFocus: Boolean,
            ): Component = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus).apply {
                (value as Node).let {
                    text = it.text
                    icon = it.icon
                }
            }
        }
    }

    private val toolbar: JToolBar = JToolBar().apply {
        isFloatable = false
        add(
            action(icon = IconCache.EXPAND, tooltip = "Expand All Rows") {
                (tree.model.root as? Node)?.visitChildren { path -> tree.expandPath(path) }
            }
        )
        add(
            action(icon = IconCache.COLLAPSE, tooltip = "Collapse All Rows") {
                (tree.model.root as? Node)?.visitChildren { path -> tree.collapsePath(path) }
            }
        )
    }

    init {
        add(toolbar, BorderLayout.NORTH)
        add(JScrollPane(tree), BorderLayout.CENTER)
    }

    private fun Node.visitChildren(path: TreePath = TreePath(this), block: Node.(TreePath) -> Unit) {
        children.forEach {
            it.visitChildren(path.pathByAddingChild(it), block)
        }
        block.invoke(this, path)
    }

    fun load(tag: Tag) {
        val child = tag.buildTreeNode()
        tree.model = DefaultTreeModel(child)
    }
}
