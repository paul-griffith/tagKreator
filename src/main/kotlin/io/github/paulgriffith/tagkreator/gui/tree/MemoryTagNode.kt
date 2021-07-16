@file:OptIn(ExperimentalStdlibApi::class)

package io.github.paulgriffith.tagkreator.gui.tree

import io.github.paulgriffith.tagkreator.gui.IconCache
import io.github.paulgriffith.tagkreator.model.AtomicTag
import io.github.paulgriffith.tagkreator.model.AtomicTag.ValueSource.Derived
import io.github.paulgriffith.tagkreator.model.AtomicTag.ValueSource.Expression
import io.github.paulgriffith.tagkreator.model.AtomicTag.ValueSource.Memory
import io.github.paulgriffith.tagkreator.model.AtomicTag.ValueSource.OPC
import io.github.paulgriffith.tagkreator.model.AtomicTag.ValueSource.Query
import io.github.paulgriffith.tagkreator.model.AtomicTag.ValueSource.Reference
import javax.swing.Icon

class EventScriptNode(override val parent: AtomicTagNode) : Node() {
    override val icon = IconCache.CODE

    override val children: List<Node> = parent.tag.eventScripts
        ?.sortedBy { it.eventId }
        ?.map { script -> SimpleValueNode(script.eventId.toString(), parent = this) }
        .orEmpty()

    override val text: String = "Tag Event Scripts"
}

class AtomicTagNode(override val tag: AtomicTag, parent: TagNode?) : TagNode(parent) {
    override val icon: Icon = when (tag.valueSource) {
        Memory -> IconCache.TAG
        Expression -> IconCache.CODE
        OPC -> IconCache.TAG
        Query -> IconCache.DATABASE
        Derived -> IconCache.EXTERNAL_LINK
        Reference -> IconCache.EXTERNAL_LINK
    }

    @OptIn(ExperimentalStdlibApi::class)
    override val children: List<Node> = buildList {
        node("Value", tag.value)
        node("DataType", tag.dataType)
        node("Expression", tag.expression, IconCache.CPU)
        node("Execution Mode", tag.executionMode, IconCache.CLOCK)
        node("OPC Server", tag.opcServer)
        node("OPC Item Path", tag.opcItemPath)
        node("Query", tag.query, IconCache.DATABASE)
        node("Query Type", tag.queryType)
        node("Source Tag Path", tag.sourceTagPath, IconCache.EXTERNAL_LINK)
        //    // derived
        //    val sourceTagPath: String? = null,
        //    @SerialName("deriveExpressionGetter")
        //    val readExpression: String? = null,
        //    @SerialName("deriveExpressionSetter")
        //    val writeExpression: String? = null,
        //    // deadband
        //    val deadband: Double? = null,
        //    val deadbandMode: DeadbandMode? = null,
        //    // scaling
        //    val scaleMode: ScaleMode? = null,
        //    val rawLow: Double? = null,
        //    val rawHigh: Double? = null,
        //    val scaledLow: Double? = null,
        //    val scaledHigh: Double? = null,
        //    val clampMode: ClampMode? = null,
        //    // eng units
        //    val engUnit: String? = null,
        //    val engLimitMode: ClampMode? = null,
        //    val engLow: Double? = null,
        //    val engHigh: Double? = null,
        //    val formatString: String? = null,
        //    // documentation
        //    val tooltip: String? = null,
        //    val documentation: String? = null,
        //    // permissions
        //    val readOnly: Boolean? = null,
        //    val readPermissions: Permissions? = null,
        //    val writePermissions: Permissions? = null,
        //    // scripts
        tag.eventScripts?.let { add(EventScriptNode(this@AtomicTagNode)) }
        //    val eventScripts: Set<EventScript>? = null,
        //    // alarms
        //    val alarms: List<Alarm> = emptyList(),
        //    val alarmEvalEnabled: Boolean = true,
        //    // history
        //    val historyEnabled: Boolean = false,
        //    val historyProvider: String? = null,
        //    val historicalDeadband: Double? = null,
        //    val historicalDeadbandMode: HistoricalDeadbandMode? = null,
        //    val historicalDeadbandStyle: HistoricalDeadbandStyle? = null,
        //    val historyMaxAge: Long? = null,
        //    val historyMaxAgeUnits: TimeUnits? = null,
        //    val historySampleRate: Long? = null,
        //    val historySampleRateUnits: TimeUnits? = null,
        //    val sampleMode: SampleMode? = null,
    }
    override val text: String = "${tag.valueSource} - ${tag.name}"
}
