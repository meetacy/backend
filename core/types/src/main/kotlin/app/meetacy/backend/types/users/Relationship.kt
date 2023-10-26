package app.meetacy.backend.types.users

sealed interface Relationship {
    data object None : Relationship
    data object Subscriber : Relationship
    data object Subscription : Relationship
    data object Friend : Relationship
}
