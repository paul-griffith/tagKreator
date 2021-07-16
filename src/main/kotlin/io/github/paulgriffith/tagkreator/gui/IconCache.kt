package io.github.paulgriffith.tagkreator.gui

import com.formdev.flatlaf.extras.FlatSVGIcon
import kotlin.properties.ReadOnlyProperty

object IconCache {
    private val cache: MutableMap<String, FlatSVGIcon> = hashMapOf()

    private fun cache(
        name: String? = null,
        customizer: (FlatSVGIcon) -> FlatSVGIcon = { it },
    ): ReadOnlyProperty<IconCache, FlatSVGIcon> {
        return ReadOnlyProperty { _, property ->
            val iconName = name ?: property.name.lowercase()
            cache.getOrPut(iconName) {
                FlatSVGIcon("io/github/paulgriffith/tagkreator/gui/$iconName.svg").let(customizer)
            }
        }
    }

    val TAG by cache()
    val FILE by cache()
    val FOLDER by cache()
    val CPU by cache()
    val CODE by cache()
    val COPY by cache()
    val CLOCK by cache()
    val DATABASE by cache()
    val NODE: FlatSVGIcon by cache("square") { it.derive(0.8F) }
    val EXPAND: FlatSVGIcon by cache("maximize-2")
    val COLLAPSE: FlatSVGIcon by cache("minimize-2")
    val EXTERNAL_LINK: FlatSVGIcon by cache()
}
