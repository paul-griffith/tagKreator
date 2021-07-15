package io.github.paulgriffith.tagkreator.gui.tree

import com.formdev.flatlaf.extras.components.FlatTree
import io.github.paulgriffith.tagkreator.gui.JSON_FILE_FILTER
import io.github.paulgriffith.tagkreator.gui.tree.TagNode.Companion.buildTreeNode
import io.github.paulgriffith.tagkreator.json.TAG_JSON
import io.github.paulgriffith.tagkreator.model.Tag
import net.miginfocom.swing.MigLayout
import java.awt.Component
import java.awt.event.ActionEvent
import javax.swing.AbstractAction
import javax.swing.JButton
import javax.swing.JFileChooser
import javax.swing.JPanel
import javax.swing.JTree
import javax.swing.tree.DefaultTreeCellRenderer
import javax.swing.tree.DefaultTreeModel

class TreeTab : JPanel(MigLayout("ins 0, fill")) {
    val fileChooser = JFileChooser().apply {
        fileFilter = JSON_FILE_FILTER
    }

    private val tree = FlatTree().apply {
        showsRootHandles = false
        model = null
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

    init {
        add(tree, "grow")
        add(
            JButton(object : AbstractAction("Open") {
                override fun actionPerformed(e: ActionEvent) {
                    val ret = fileChooser.showOpenDialog(this@TreeTab)
                    if (ret == JFileChooser.APPROVE_OPTION) {
                        val tag = TAG_JSON.decodeFromString(Tag.serializer(), fileChooser.selectedFile.readText())
                        val child = tag.buildTreeNode()
                        tree.model = DefaultTreeModel(child)
                    }
                }
            }),
            "dock south"
        )
    }
}
