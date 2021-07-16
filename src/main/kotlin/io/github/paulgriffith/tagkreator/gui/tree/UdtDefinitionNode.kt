package io.github.paulgriffith.tagkreator.gui.tree

import io.github.paulgriffith.tagkreator.gui.IconCache
import io.github.paulgriffith.tagkreator.model.UdtDef

class UdtDefinitionNode(override val tag: UdtDef, parent: TagNode?) : TagNode(parent) {
    override val icon = IconCache.FOLDER // TODO type color?
    override val children: List<TagNode> = tag.tags
        .sortedBy { it.name }
        .map { it.buildTreeNode(this) }
    override val text = "UDT Def - ${tag.name}"
}
