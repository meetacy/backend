package app.meetacy.backend.types.serialization.user

import app.meetacy.backend.types.user.Relationship
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class RelationshipSerializable(val string: String) {
    fun type() = Relationship.parse(string)!!
}

fun Relationship.serializable() = RelationshipSerializable(string)
