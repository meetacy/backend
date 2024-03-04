package app.meetacy.backend.types.serializable.users

import app.meetacy.backend.types.serializable.amount.Amount
import app.meetacy.backend.types.serializable.file.FileIdentity
import kotlinx.serialization.Serializable

@Serializable
data class UserDetails(
    val isSelf: Boolean,
    val relationship: Relationship?,
    val id: UserIdentity,
    val nickname: String,
    val username: Username?,
    val email: String?,
    val emailVerified: Boolean?,
    val avatarId: FileIdentity?,
    val subscribersAmount: Amount.OrZero,
    val subscriptionsAmount: Amount.OrZero
)
