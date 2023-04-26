package app.meetacy.backend.usecase.types

import app.meetacy.backend.types.file.FileId
import app.meetacy.backend.types.file.FileIdentity
import app.meetacy.backend.types.gender.UserGender
import app.meetacy.backend.types.user.UserIdentity


data class FullUser(
    val identity: UserIdentity,
    val gender: UserGender?,
    val nickname: String,
    val email: String?,
    val emailVerified: Boolean?,
    val avatarId: FileId?
)

data class UserView(
    val isSelf: Boolean,
    val friendship: Friendship?,
    val identity: UserIdentity,
    val gender: UserGender?,
    val nickname: String,
    val email: String?,
    val emailVerified: Boolean?,
    val avatarIdentity: FileIdentity?
) {
    enum class Friendship {
        None, Subscription, Subscriber, Friends;
    }
}
