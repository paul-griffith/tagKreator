package io.github.paulgriffith.tagkreator.gui

import com.formdev.flatlaf.FlatDarculaLaf
import com.formdev.flatlaf.extras.components.FlatTabbedPane
import com.formdev.flatlaf.extras.components.FlatTriStateCheckBox
import io.github.paulgriffith.tagkreator.gui.tree.TreeTab
import net.miginfocom.swing.MigLayout
import java.io.File
import javax.swing.JCheckBox
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.JTextField
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
            addTab(
                "Open",
                null,
                OpenTab(),
                "Select multiple files to open and verify",
            )
            addTab(
                "LaF Testing",
                JPanel(MigLayout()).apply {
                    add(FlatTriStateCheckBox("tristate"))
                    add(JCheckBox("checkbox"))
                    add(JTextField())
                }
            )
        }
    }
}

fun main() {
    FlatDarculaLaf.setup()
    TagKreatorGui().also { gui ->
        gui.isVisible = true
    }
}

internal val JSON_FILE_FILTER = object : FileFilter() {
    override fun accept(f: File): Boolean = f.extension.lowercase() == "json"
    override fun getDescription(): String = "Tag Export(s) [.json]"
}
