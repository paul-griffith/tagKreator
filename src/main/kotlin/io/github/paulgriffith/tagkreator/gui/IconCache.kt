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

    val CLOCK by cache()
    val CODE by cache()
    val COLLAPSE by cache("minimize-2")
    val COPY by cache()
    val CPU by cache()
    val DATABASE by cache()
    val EXPAND by cache("maximize-2")
    val EXTERNAL_LINK by cache()
    val FILE by cache()
    val FOLDER by cache()
    val NODE by cache("square") { it.derive(0.8F) }
    val SAVE by cache()
    val TAG by cache()
}
