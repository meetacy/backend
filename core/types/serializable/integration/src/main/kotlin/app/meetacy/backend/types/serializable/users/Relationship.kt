package app.meetacy.backend.types.serializable.users

import app.meetacy.backend.types.users.Relationship
import app.meetacy.backend.types.serializable.users.Relationship as RelationshipSerializable

fun RelationshipSerializable.type() = Relationship.parse(string)!!
fun Relationship.serializable() = RelationshipSerializable(string)
