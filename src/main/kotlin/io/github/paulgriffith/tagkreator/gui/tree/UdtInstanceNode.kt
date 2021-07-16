package io.github.paulgriffith.tagkreator.gui.tree

import io.github.paulgriffith.tagkreator.gui.IconCache
import io.github.paulgriffith.tagkreator.model.UdtInstance

class UdtInstanceNode(override val tag: UdtInstance, parent: TagNode?) : TagNode(parent) {
    override val icon = IconCache.FOLDER
    override val children: List<TagNode> = tag.tags
        ?.sortedBy { it.name }
        ?.map { it.buildTreeNode(this) }
        .orEmpty()
    override val text = "UDT Instance - ${tag.name}"
}
