package app.meetacy.backend.types.serializable.users

import app.meetacy.backend.types.serializable.amount.serializable
import app.meetacy.backend.types.serializable.datetime.serializable
import app.meetacy.backend.types.serializable.file.serializable
import app.meetacy.backend.types.serializable.location.serializable
import app.meetacy.backend.types.users.UserDetails
import app.meetacy.backend.types.users.UserLocationSnapshot
import app.meetacy.backend.types.users.UserView
import app.meetacy.backend.types.serializable.users.UserDetails as UserDetailsSerializable
import app.meetacy.backend.types.serializable.users.UserLocationSnapshot as UserLocationSnapshotSerializable

fun UserView.serializable() = User(
    isSelf = isSelf,
    relationship = relationship?.serializable(),
    id = identity.serializable(),
    nickname = nickname,
    email = email,
    emailVerified = emailVerified,
    username = username?.serializable(),
    avatarId = avatarIdentity?.serializable()
)

fun UserDetails.serializable() = UserDetailsSerializable(
    isSelf = isSelf,
    relationship = relationship?.serializable(),
    id = identity.serializable(),
    nickname = nickname,
    username = username?.serializable(),
    email = email,
    emailVerified = emailVerified,
    avatarId = avatarIdentity?.serializable(),
    subscribersAmount = subscribersAmount.serializable(),
    subscriptionsAmount = subscriptionsAmount.serializable(),
)

fun UserLocationSnapshot.serializable() = UserLocationSnapshotSerializable(
    user = user.serializable(),
    location = location.serializable(),
    capturedAt = capturedAt.serializable()
)
