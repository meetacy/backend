package app.meetacy.backend.endpoint.types

import app.meetacy.backend.types.serialization.file.FileIdentitySerializable
import app.meetacy.backend.types.serialization.gender.UserGenderSerializable
import app.meetacy.backend.types.serialization.user.UserIdentitySerializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val isSelf: Boolean,
    val friendship: Friendship?,
    val id: UserIdentitySerializable,
    val gender: UserGenderSerializable?,
    val nickname: String,
    val email: String?,
    val emailVerified: Boolean?,
    val avatarIdentity: FileIdentitySerializable?
) {
    @Serializable
    enum class Friendship {
        @SerialName("none") None,
        @SerialName("subscription") Subscription,
        @SerialName("subscriber") Subscriber,
        @SerialName("friends") Friends;
    }
}
