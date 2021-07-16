package io.github.paulgriffith.tagkreator.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull

@Serializable
@SerialName("AtomicTag")
data class AtomicTag(
    override val name: String,
    val valueSource: ValueSource = ValueSource.Memory,
    val value: JsonElement = JsonNull,
    val dataType: DataType = DataType.Int4,
    val tagGroup: String? = null,
    // expression (technically, expression should be JsonPrimitive, but numeric literals are uncommon)
    val expression: String? = null,
    val executionMode: TagExecutionMode? = TagExecutionMode.TagGroupRate,
    // opc
    val opcItemPath: String? = null,
    val opcServer: String? = null,
    // query
    val query: String? = null,
    val queryType: QueryType? = null,
    // derived
    val sourceTagPath: String? = null,
    @SerialName("deriveExpressionGetter")
    val readExpression: String? = null,
    @SerialName("deriveExpressionSetter")
    val writeExpression: String? = null,
    // deadband
    val deadband: Double? = null,
    val deadbandMode: DeadbandMode? = null,
    // scaling
    val scaleMode: ScaleMode? = null,
    val rawLow: Double? = null,
    val rawHigh: Double? = null,
    val scaledLow: Double? = null,
    val scaledHigh: Double? = null,
    val clampMode: ClampMode? = null,
    // eng units
    val engUnit: String? = null,
    val engLimitMode: ClampMode? = null,
    val engLow: Double? = null,
    val engHigh: Double? = null,
    val formatString: String? = null,
    // documentation
    val tooltip: String? = null,
    val documentation: String? = null,
    // permissions
    val readOnly: Boolean? = null,
    val readPermissions: Permissions? = null,
    val writePermissions: Permissions? = null,
    // scripts
    val eventScripts: Set<EventScript>? = null,
    // alarms
    val alarms: List<Alarm> = emptyList(),
    val alarmEvalEnabled: Boolean = true,
    // history
    val historyEnabled: Boolean = false,
    val historyProvider: String? = null,
    val historicalDeadband: Double? = null,
    val historicalDeadbandMode: HistoricalDeadbandMode? = null,
    val historicalDeadbandStyle: HistoricalDeadbandStyle? = null,
    val historyTagGroup: String? = null,
    val historyMaxAge: Long? = null,
    val historyMaxAgeUnits: TimeUnits? = null,
    val historySampleRate: Long? = null,
    val historySampleRateUnits: TimeUnits? = null,
    val sampleMode: SampleMode? = null,
) : Tag() {
    @Serializable
    enum class DataType {
        Int1,
        Int2,
        Int4,
        Int8,
        Float4,
        Float8,
        Boolean,
        String,
        DateTime,
        Text,
        Int1Array,
        Int2Array,
        Int4Array,
        Int8Array,
        Float4Array,
        Float8Array,
        BooleanArray,
        StringArray,
        DateTimeArray,
        ByteArray,
        DataSet,
        Document;
    }

    @Serializable
    enum class ValueSource {
        @SerialName("memory")
        Memory,

        @SerialName("expr")
        Expression,

        @SerialName("opc")
        OPC,

        @SerialName("db")
        Query,

        @SerialName("derived")
        Derived,

        @SerialName("reference")
        Reference;
    }

    @Serializable
    enum class TagExecutionMode {
        EventDriven,
        FixedRate,
        TagGroupRate;
    }

    @Serializable
    enum class DeadbandMode {
        Analog,
        Percent;
    }

    @Serializable
    enum class HistoricalDeadbandMode {
        Absolute,
        Percent,
        Off;
    }

    @Serializable
    enum class HistoricalDeadbandStyle {
        Discrete,
        Analog,
        Analog_Deadband,
        Analog_Compressed,
        Auto;
    }

    @Serializable
    enum class SampleMode {
        OnChange,
        Periodic,
        TagGroup;
    }

    @Serializable
    enum class QueryType {
        AutoDetect,
        Select,
        Update;
    }

    @Serializable
    data class Permissions(
        val securityLevels: List<SecurityLevel>,
        val type: Type,
    ) {
        @Serializable
        data class SecurityLevel(
            val name: String,
            val children: List<SecurityLevel>,
        )

        @Serializable
        enum class Type {
            AllOf,
            AnyOf;
        }
    }

    @Serializable
    data class EventScript(
        @SerialName("eventid")
        val eventId: EventId,
        val script: String,
        val enabled: Boolean = true,
    ) {
        override fun equals(other: Any?): Boolean {
            return eventId == (other as? EventScript)?.eventId
        }

        override fun hashCode(): Int {
            return eventId.hashCode()
        }

        @Serializable
        enum class EventId {
            @SerialName("valueChanged")
            VALUE_CHANGED,

            @SerialName("qualityChanged")
            QUALITY_CHANGED,

            @SerialName("alarmActive")
            ALARM_ACTIVE,

            @SerialName("alarmCleared")
            ALARM_CLEARED,

            @SerialName("alarmAcked")
            ALARM_ACKED;
        }
    }

    @Serializable
    enum class TimeUnits {
        MS,
        SEC,
        MIN,
        HOUR,
        DAY,
        WEEK,
        MONTH,
        YEAR;
    }

    @Serializable
    enum class ScaleMode {
        Off,
        Linear,
        SquareRoot,
        ExponentialFilter,
        BitInversion;
    }

    @Serializable
    enum class ClampMode {
        No_Clamp,
        Clamp_Low,
        Clamp_High,
        Clamp_Both;
    }
}
