package io.github.paulgriffith.tagkreator.gui.tree

import com.formdev.flatlaf.extras.components.FlatTextArea
import io.github.paulgriffith.tagkreator.gui.IconCache
import io.github.paulgriffith.tagkreator.gui.TAG_FILE_CHOOSER
import io.github.paulgriffith.tagkreator.gui.action
import io.github.paulgriffith.tagkreator.json.EXPORT_JSON
import io.github.paulgriffith.tagkreator.json.TAG_JSON
import io.github.paulgriffith.tagkreator.model.Tag
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.encodeToString
import java.awt.BorderLayout
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import javax.swing.JFileChooser
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JToolBar
import kotlin.io.path.writeText
import kotlin.properties.Delegates

class PreviewPanel : JPanel(BorderLayout()) {
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
        add(
            action(icon = IconCache.SAVE, tooltip = "Save") {
                val selectedNode = selection
                if (selectedNode is TagNode) {
                    val ret = TAG_FILE_CHOOSER.showSaveDialog(this@PreviewPanel)
                    if (ret == JFileChooser.APPROVE_OPTION) {
                        TAG_FILE_CHOOSER.selectedFile.writeText(EXPORT_JSON.encodeToString(selectedNode.tag))
                    }
                }
            }
        )
    }

    var selection: Node? by Delegates.observable(null) { _, _, newValue ->
        textArea.text = when (newValue) {
            is TagNode -> TAG_JSON.encodeToString(Tag.serializer().nullable, newValue.tag)
            is EventScriptNode -> newValue.text
            is SimpleValueNode -> newValue.text
            null -> null
        }
    }

    init {
        add(toolbar, BorderLayout.NORTH)
        add(JScrollPane(textArea), BorderLayout.CENTER)
    }
}
