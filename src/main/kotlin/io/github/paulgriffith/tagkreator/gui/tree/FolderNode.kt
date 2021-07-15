package io.github.paulgriffith.tagkreator.gui.tree

import io.github.paulgriffith.tagkreator.gui.IconCache
import io.github.paulgriffith.tagkreator.model.Folder

class FolderNode(tag: Folder, parent: TagNode?) : TagNode(parent) {
    override val icon = IconCache.FOLDER
    override val children: List<TagNode> = tag.tags.map { it.buildTreeNode(this) }
    override val text = "Folder - ${tag.name}"
}
