package app.meetacy.backend.types.serializable.users

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Relationship {
    @SerialName("none")
    None,
    @SerialName("subscriber")
    Subscriber,
    @SerialName("subscription")
    Subscription,
    @SerialName("friend")
    Friend
}
