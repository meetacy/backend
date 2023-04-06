package app.meetacy.backend.types.serialization.datetime

import app.meetacy.backend.types.datetime.DateOrTime
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class DateOrTimeSerializable(val string: String) {
    fun type(): DateOrTime = DateOrTime.parse(string)
}

fun DateOrTime.serializable(): DateOrTimeSerializable = DateOrTimeSerializable(iso8601)
