package app.meetacy.backend.types.serializable.datetime

import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.serializable.datetime.DateTime as DateTimeSerializable

fun DateTimeSerializable.type() = DateTime.parse(iso8601)
fun DateTime.serializable() = DateTimeSerializable(iso8601)
