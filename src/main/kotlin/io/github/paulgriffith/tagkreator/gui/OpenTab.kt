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
    init {
        add(
            JButton(object : AbstractAction("Open") {
                override fun actionPerformed(e: ActionEvent) {
                    val ret = TAG_FILE_CHOOSER.showOpenDialog(this@OpenTab)
                    if (ret == JFileChooser.APPROVE_OPTION) {
                        TAG_FILE_CHOOSER.selectedFiles.map {
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
