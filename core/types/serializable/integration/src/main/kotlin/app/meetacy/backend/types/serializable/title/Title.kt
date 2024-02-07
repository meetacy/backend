package app.meetacy.backend.types.serializable.title

import  app.meetacy.backend.types.title.Title
import app.meetacy.backend.types.serializable.serialization
import app.meetacy.backend.types.serializable.title.Title as TitleSerializable

fun TitleSerializable.type() = serialization { Title.parse(string) }
fun Title.serializable(): TitleSerializable = TitleSerializable(string)
