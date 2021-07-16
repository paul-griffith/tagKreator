package io.github.paulgriffith.tagkreator.gui.tree

import com.formdev.flatlaf.extras.components.FlatTextArea
import com.formdev.flatlaf.extras.components.FlatTree
import io.github.paulgriffith.tagkreator.gui.IconCache
import io.github.paulgriffith.tagkreator.gui.JSON_FILE_FILTER
import io.github.paulgriffith.tagkreator.gui.action
import io.github.paulgriffith.tagkreator.gui.tree.TagNode.Companion.buildTreeNode
import io.github.paulgriffith.tagkreator.json.TAG_JSON
import io.github.paulgriffith.tagkreator.model.Tag
import net.miginfocom.swing.MigLayout
import java.awt.BorderLayout
import java.awt.Component
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import javax.swing.JFileChooser
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JToolBar
import javax.swing.JTree
import javax.swing.ListSelectionModel.SINGLE_SELECTION
import javax.swing.tree.DefaultTreeCellRenderer
import javax.swing.tree.DefaultTreeModel
import javax.swing.tree.TreePath

class TreeTab : JPanel(MigLayout("ins 0, fill")) {
    private val fileChooser = JFileChooser().apply {
        fileFilter = JSON_FILE_FILTER
    }

    private val toolbar = JToolBar().apply {
        isFloatable = false
        add(
            action(name = "Open", icon = IconCache.FILE) {
                val ret = fileChooser.showOpenDialog(this@TreeTab)
                if (ret == JFileChooser.APPROVE_OPTION) {
                    val tag = TAG_JSON.decodeFromString(Tag.serializer(), fileChooser.selectedFile.readText())
                    tagTree.load(tag)
                }
            }
        )
    }

    private val tagTree = TagTree().apply {
        tree.addTreeSelectionListener {
            preview.text = (it.path.lastPathComponent as? Node)?.let { selection ->
                when (selection) {
                    is TagNode -> TAG_JSON.encodeToString(Tag.serializer(), selection.tag)
                    is EventScriptNode -> null
                    is SimpleValueNode -> selection.text
                }
            }
        }
    }

    private val preview: PreviewPanel = PreviewPanel()

    init {
        add(toolbar, "dock north")
        add(tagTree, "grow")
        add(preview, "dock east, width 300px")
    }
}

class TagTree : JPanel(BorderLayout()) {
    val tree = FlatTree().apply {
        showsRootHandles = true
        model = null
        selectionModel.selectionMode = SINGLE_SELECTION
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

private class PreviewPanel : JPanel(BorderLayout()) {
    private val textArea = FlatTextArea().apply {
        isEditable = false
    }

    private val toolbar = JToolBar().apply {
        isFloatable = false
        add(
            action(icon = IconCache.COPY, tooltip = "Copy to Clipboard") {
                Toolkit.getDefaultToolkit().systemClipboard.setContents(StringSelection(textArea.text), null)
            }
        )
    }

    var text: String? by textArea::text

    init {
        add(toolbar, BorderLayout.NORTH)
        add(JScrollPane(textArea), BorderLayout.CENTER)
    }
}
