package app.meetacy.backend.types.serializable.datetime

import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.serializable.serialization
import app.meetacy.backend.types.serializable.datetime.Date as DateSerializable

fun DateSerializable.type() = serialization { Date.parse(iso8601) }
fun Date.serializable() = DateSerializable(iso8601)
