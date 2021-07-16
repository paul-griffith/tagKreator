package io.github.paulgriffith.tagkreator.gui.tree

import io.github.paulgriffith.tagkreator.gui.IconCache
import io.github.paulgriffith.tagkreator.gui.TAG_FILE_CHOOSER
import io.github.paulgriffith.tagkreator.gui.action
import io.github.paulgriffith.tagkreator.json.TAG_JSON
import io.github.paulgriffith.tagkreator.model.Tag
import net.miginfocom.swing.MigLayout
import java.awt.FlowLayout
import javax.swing.JButton
import javax.swing.JFileChooser
import javax.swing.JPanel

class TreeTab : JPanel(MigLayout("ins 0, fill")) {
    private val actions = JPanel(FlowLayout(FlowLayout.LEADING)).apply {
        add(
            JButton(
                action(name = "Open", icon = IconCache.FILE) {
                    val ret = TAG_FILE_CHOOSER.showOpenDialog(this@TreeTab)
                    if (ret == JFileChooser.APPROVE_OPTION) {
                        val tag = TAG_JSON.decodeFromString(Tag.serializer(), TAG_FILE_CHOOSER.selectedFile.readText())
                        tagTree.load(tag)
                    }
                }
            )
        )
    }

    private val tagTree = TagTree().apply {
        tree.addTreeSelectionListener {
            preview.selection = (it.path.lastPathComponent as? Node)
        }
    }

    private val preview: PreviewPanel = PreviewPanel()

    init {
        add(actions, "dock north")
        add(tagTree, "grow")
        add(preview, "dock east, width 300px")
    }
}
