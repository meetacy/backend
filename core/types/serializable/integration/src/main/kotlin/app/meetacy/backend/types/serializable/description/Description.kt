package app.meetacy.backend.types.serializable.description

import  app.meetacy.backend.types.description.Description
import app.meetacy.backend.types.serializable.serialization
import app.meetacy.backend.types.serializable.description.Description as DescriptionSerializable

fun DescriptionSerializable.type() = serialization { Description.parse(string) }
fun Description.serializable(): DescriptionSerializable = DescriptionSerializable(string)
