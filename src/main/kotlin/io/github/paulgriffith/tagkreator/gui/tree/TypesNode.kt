package io.github.paulgriffith.tagkreator.gui.tree

import io.github.paulgriffith.tagkreator.gui.IconCache
import io.github.paulgriffith.tagkreator.model.Folder

class TypesNode(override val tag: Folder, parent: TagNode?) : TagNode(parent) {
    override val icon = IconCache.FOLDER
    override val children: List<TagNode> = tag.tags
        .sortedBy { it.name }
        .map { it.buildTreeNode(this) }
    override val text = "Data Types"
}