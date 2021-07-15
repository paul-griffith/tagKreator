package io.github.paulgriffith.tagkreator.gui

import com.formdev.flatlaf.extras.FlatSVGIcon
import javax.swing.Icon

object IconCache {
    val TAG: Icon by lazy { get("tag") }
    val FILE: Icon by lazy { get("file") }
    val FOLDER: Icon by lazy { get("folder") }
    val NODE: Icon by lazy { get("square") }

    private fun get(key: String): Icon {
//        val rs = javaClass.getResourceAsStream("$key.svg")
        return FlatSVGIcon("io/github/paulgriffith/tagkreator/gui/$key.svg")
    }
}
