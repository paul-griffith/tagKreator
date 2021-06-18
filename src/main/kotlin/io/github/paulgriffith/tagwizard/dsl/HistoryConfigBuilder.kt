package io.github.paulgriffith.tagwizard.dsl

import io.github.paulgriffith.tagwizard.model.AtomicTag
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@TagDslMarker
class HistoryConfigBuilder {
    var enabled: Boolean = true
    lateinit var provider: String
    var deadband: Double? = null
    var deadbandMode: AtomicTag.HistoricalDeadbandMode? = null
    var deadbandStyle: AtomicTag.HistoricalDeadbandStyle? = null
    var maxAge: Duration? = null
    var sampleRate: Duration? = null
    var sampleMode: AtomicTag.SampleMode? = null

    val config: TagConfig
        get() = {
            copy(
                historyEnabled = enabled,
                historyProvider = provider,
                historicalDeadband = this@HistoryConfigBuilder.deadband,
                historicalDeadbandMode = this@HistoryConfigBuilder.deadbandMode,
                historicalDeadbandStyle = deadbandStyle,
                historyMaxAge = maxAge?.inWholeSeconds,
                historyMaxAgeUnits = maxAge?.let { AtomicTag.TimeUnits.SEC },
                historySampleRate = sampleRate?.inWholeSeconds,
                historySampleRateUnits = sampleRate?.let { AtomicTag.TimeUnits.SEC },
                sampleMode = this@HistoryConfigBuilder.sampleMode,
            )
        }
}
