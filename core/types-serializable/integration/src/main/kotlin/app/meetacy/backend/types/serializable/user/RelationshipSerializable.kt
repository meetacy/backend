package app.meetacy.backend.types.serializable.user

import app.meetacy.backend.types.user.Relationship

fun RelationshipSerializable.type() = Relationship.parse(string)!!

fun Relationship.serializable() = RelationshipSerializable(string)
