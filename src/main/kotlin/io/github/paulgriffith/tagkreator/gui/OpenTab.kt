package io.github.paulgriffith.tagkreator.gui

import io.github.paulgriffith.tagkreator.json.TAG_JSON
import io.github.paulgriffith.tagkreator.model.Tag
import net.miginfocom.swing.MigLayout
import java.awt.event.ActionEvent
import javax.swing.AbstractAction
import javax.swing.JButton
import javax.swing.JFileChooser
import javax.swing.JPanel

class OpenTab : JPanel(MigLayout()) {
    val fileChooser = JFileChooser().apply {
        isMultiSelectionEnabled = true
        fileFilter = JSON_FILE_FILTER
    }

    init {
        add(
            JButton(object : AbstractAction("Open") {
                override fun actionPerformed(e: ActionEvent) {
                    val ret = fileChooser.showOpenDialog(this@OpenTab)
                    if (ret == JFileChooser.APPROVE_OPTION) {
                        fileChooser.selectedFiles.map {
                            TAG_JSON.decodeFromString(Tag.serializer(), it.readText())
                        }.forEach { tag ->
                            println(tag)
                        }
                    }
                }
            })
        )
    }
}
