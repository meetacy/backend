package app.meetacy.backend.types.serializable.user

import app.meetacy.backend.types.user.Relationship

fun app.meetacy.backend.types.serializable.user.Relationship.type() = Relationship.parse(string)!!

fun Relationship.serializable() = Relationship(string)
