package app.meetacy.backend.usecase.types

import app.meetacy.backend.types.file.FileId
import app.meetacy.backend.types.file.FileIdentity
import app.meetacy.backend.types.user.UserIdentity


data class FullUser(
    val identity: UserIdentity,
    val nickname: String,
    val email: String?,
    val emailVerified: Boolean?,
    val avatarId: FileId?
)

data class UserView(
    val isSelf: Boolean,
    val isFriend: UsecaseRelationship?,
    val identity: UserIdentity,
    val nickname: String,
    val email: String?,
    val emailVerified: Boolean?,
    val avatarIdentity: FileIdentity?
)

sealed interface UsecaseRelationship {
    object Friend: UsecaseRelationship
    object Subscriber: UsecaseRelationship
    object Subscribed: UsecaseRelationship
    object None: UsecaseRelationship
}
