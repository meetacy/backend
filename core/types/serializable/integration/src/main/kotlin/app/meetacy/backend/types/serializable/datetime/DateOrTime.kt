package app.meetacy.backend.types.serializable.datetime

import app.meetacy.backend.types.datetime.DateOrTime
import app.meetacy.backend.types.serializable.datetime.DateOrTime as DateOrTimeSerializable

fun DateOrTimeSerializable.type(): DateOrTime = DateOrTime.parse(string)
fun DateOrTime.serializable(): DateOrTimeSerializable = DateOrTimeSerializable(iso8601)
