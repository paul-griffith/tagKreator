package io.github.paulgriffith.tagkreator.gui.tree

import io.github.paulgriffith.tagkreator.gui.IconCache
import io.github.paulgriffith.tagkreator.model.AtomicTag
import io.github.paulgriffith.tagkreator.model.Folder
import io.github.paulgriffith.tagkreator.model.Provider
import io.github.paulgriffith.tagkreator.model.Tag
import io.github.paulgriffith.tagkreator.model.UdtDef
import io.github.paulgriffith.tagkreator.model.UdtInstance
import javax.swing.Icon

sealed class TagNode(override val parent: TagNode?) : Node() {
    abstract val tag: Tag

    protected fun MutableList<Node>.node(field: String, value: Any?, icon: Icon? = IconCache.NODE) {
        if (value != null) {
            add(SimpleValueNode(text = "$field: $value", icon = icon, parent = this@TagNode))
        }
    }

    companion object {
        fun Tag.buildTreeNode(parent: TagNode? = null): TagNode {
            return when (this) {
                is AtomicTag -> AtomicTagNode(this, parent)
                is Folder -> if (this.name == Tag.TYPES_FOLDER_NAME) {
                    TypesNode(this, parent)
                } else {
                    FolderNode(this, parent)
                }
                is Provider -> ProviderNode(this, parent)
                is UdtDef -> UdtDefinitionNode(this, parent)
                is UdtInstance -> UdtInstanceNode(this, parent)
            }
        }
    }
}
