package io.github.paulgriffith.tagkreator.gui.tree

import io.github.paulgriffith.tagkreator.gui.IconCache
import io.github.paulgriffith.tagkreator.model.UdtDef

class UdtDefinitionNode(tag: UdtDef, parent: TagNode?) : TagNode(parent) {
    override val icon = IconCache.FOLDER
    override val children: List<TagNode> = tag.tags.map { it.buildTreeNode(this) }
    override val text = "UDT Def - ${tag.name}"
}
