package io.github.paulgriffith.tagkreator.model

import io.github.paulgriffith.tagkreator.json.BindableAckModeSerializer
import io.github.paulgriffith.tagkreator.json.BindableBooleanSerializer
import io.github.paulgriffith.tagkreator.json.BindablePrioritySerializer
import io.github.paulgriffith.tagkreator.json.BindableStringSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Alarm(
    val name: String,
    @Serializable(with = BindableBooleanSerializer::class)
    val enabled: Bindable<Boolean> = Direct(true),
    @Serializable(with = BindablePrioritySerializer::class)
    val priority: Bindable<Priority> = Direct(Priority.Low),
    val timestampSource: TimestampSource = TimestampSource.System,
    @Serializable(with = BindableStringSerializer::class)
    val label: Bindable<String>? = null,
    @Serializable(with = BindableStringSerializer::class)
    val displayPath: Bindable<String>? = null,
    @Serializable(with = BindableAckModeSerializer::class)
    val ackMode: Bindable<AckMode> = Direct(AckMode.Auto),
    val notes: String? = null,
    @Serializable(with = BindableBooleanSerializer::class)
    @SerialName("ackNotesReqd")
    val ackNotesRequired: Bindable<Boolean> = Direct(false),
    @Serializable(with = BindableBooleanSerializer::class)
    val shelvingAllowed: Bindable<Boolean> = Direct(false),
    // TODO: Associated data
) {
    @Serializable
    enum class Priority {
        Diagnostic,
        Low,
        Medium,
        High,
        Critical;
    }

    @Serializable
    enum class TimestampSource {
        System,
        Value
    }

    @Serializable
    enum class AckMode {
        Unused,
        Auto,
        Manual
    }

    @Serializable
    sealed class Bindable<T>

    @Serializable
    data class Direct<T>(val value: T) : Bindable<T>()

    @Serializable
    data class TagBinding<T>(val value: String) : Bindable<T>()

    @Serializable
    data class ExprBinding<T>(val value: String) : Bindable<T>()
}
