package app.meetacy.backend.types.serializable.users

import app.meetacy.backend.types.users.Relationship
import app.meetacy.backend.types.serializable.users.Relationship as RelationshipSerializable

fun RelationshipSerializable.type() = when (this) {
    RelationshipSerializable.None -> Relationship.None
    RelationshipSerializable.Subscriber -> Relationship.Subscriber
    RelationshipSerializable.Subscription -> Relationship.Subscription
    RelationshipSerializable.Friend -> Relationship.Friend
}

fun Relationship.serializable() = when (this) {
    Relationship.None -> RelationshipSerializable.None
    Relationship.Subscriber -> RelationshipSerializable.Subscriber
    Relationship.Subscription -> RelationshipSerializable.Subscription
    Relationship.Friend -> RelationshipSerializable.Friend
}
