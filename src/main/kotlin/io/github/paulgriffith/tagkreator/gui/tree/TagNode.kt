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
    protected fun node(text: String, icon: Icon? = IconCache.NODE): SimpleValueNode = SimpleValueNode(text, icon, this)

    companion object {
        fun Tag.buildTreeNode(parent: TagNode? = null): TagNode {
            return when (this) {
                is AtomicTag -> when (valueSource) {
                    AtomicTag.ValueSource.Memory -> MemoryTagNode(this, parent)
                    AtomicTag.ValueSource.Expression -> ExpressionTagNode(this, parent)
                    AtomicTag.ValueSource.OPC -> OPCTagNode(this, parent)
                    AtomicTag.ValueSource.Query -> TODO()
                    AtomicTag.ValueSource.Derived -> TODO()
                    AtomicTag.ValueSource.Reference -> TODO()
                }
                is Folder -> FolderNode(this, parent)
                is Provider -> ProviderNode(this, parent)
                is UdtDef -> UdtDefinitionNode(this, parent)
                is UdtInstance -> UdtInstanceNode(this, parent)
            }
        }
    }
}
