package app.meetacy.backend.types.serializable.users

import app.meetacy.backend.types.serializable.datetime.serializable
import app.meetacy.backend.types.serializable.datetime.type
import app.meetacy.backend.types.serializable.file.serializable
import app.meetacy.backend.types.serializable.file.type
import app.meetacy.backend.types.serializable.location.serializable
import app.meetacy.backend.types.serializable.location.type
import app.meetacy.backend.types.serializable.serialization
import app.meetacy.backend.types.users.UserLocationSnapshot
import app.meetacy.backend.types.users.UserView
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

fun UserLocationSnapshot.serializable() = UserLocationSnapshotSerializable(
    user = user.serializable(),
    location = location.serializable(),
    capturedAt = capturedAt.serializable()
)

fun User.type(): UserView = serialization {
    UserView(
        isSelf = isSelf,
        relationship = relationship?.type(),
        identity = id.type(),
        nickname = nickname,
        email = email,
        emailVerified = emailVerified,
        username = username?.type(),
        avatarIdentity = avatarId?.type()
    )
}

fun UserLocationSnapshotSerializable.type() = serialization {
    UserLocationSnapshot(
        user = user.type(),
        location = location.type(),
        capturedAt = capturedAt.type()
    )
}
