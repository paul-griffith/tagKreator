package io.github.paulgriffith.tagkreator.gui.tree

import io.github.paulgriffith.tagkreator.gui.IconCache
import io.github.paulgriffith.tagkreator.model.Provider
import io.github.paulgriffith.tagkreator.model.Tag

class ProviderNode(override val tag: Provider, parent: TagNode?) : TagNode(parent) {
    override val icon = IconCache.FOLDER
    override val children: List<TagNode> = tag.tags
        .sortedWith { o1: Tag, o2: Tag ->
            when {
                o1.name == Tag.TYPES_FOLDER_NAME -> -1
                o2.name == Tag.TYPES_FOLDER_NAME -> 1
                else -> compareValuesBy(o1, o2) { it.name }
            }
        }
        .map { it.buildTreeNode(this) }
    override val text = "Tags"
}
