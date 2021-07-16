package io.github.paulgriffith.tagkreator.gui

import com.formdev.flatlaf.FlatDarculaLaf
import com.formdev.flatlaf.extras.components.FlatTabbedPane
import io.github.paulgriffith.tagkreator.gui.tree.TreeTab
import java.awt.event.ActionEvent
import java.io.File
import javax.swing.AbstractAction
import javax.swing.Action
import javax.swing.Icon
import javax.swing.JFileChooser
import javax.swing.JFrame
import javax.swing.WindowConstants
import javax.swing.filechooser.FileFilter

class TagKreatorGui : JFrame("TagKreator") {
    init {
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        setSize(800, 600)

        contentPane = FlatTabbedPane().apply {
            addTab(
                "Tree",
                null,
                TreeTab(),
                "Select a file to view it in a tree",
            )
//            addTab(
//                "Open",
//                null,
//                OpenTab(),
//                "Select multiple files to open and verify",
//            )
//            addTab(
//                "LaF Testing",
//                JPanel(MigLayout()).apply {
//                    add(FlatTriStateCheckBox("tristate"))
//                    add(JCheckBox("checkbox"))
//                    add(JTextField())
//                }
//            )
        }
    }
}

fun main() {
    FlatDarculaLaf.setup()
    TagKreatorGui().apply {
        isVisible = true
    }
}

// Lazy so that it's not initialized before the LaF is set up
val TAG_FILE_CHOOSER by lazy {
    JFileChooser().apply {
        fileFilter = object : FileFilter() {
            override fun accept(f: File): Boolean = f.extension.lowercase() == "json"
            override fun getDescription(): String = "Tag Export(s) [.json]"
        }
    }
}

fun action(
    name: String? = null,
    icon: Icon? = null,
    tooltip: String? = null,
    actionPerformed: (event: ActionEvent) -> Unit,
): AbstractAction = object : AbstractAction(name) {
    init {
        putValue(Action.SMALL_ICON, icon)
        putValue(Action.SHORT_DESCRIPTION, tooltip)
    }

    override fun actionPerformed(e: ActionEvent) = actionPerformed(e)
}
