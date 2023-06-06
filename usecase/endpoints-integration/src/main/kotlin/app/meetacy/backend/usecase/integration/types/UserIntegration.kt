package app.meetacy.backend.usecase.integration.types

import app.meetacy.backend.endpoint.types.User
import app.meetacy.backend.types.serialization.datetime.serializable
import app.meetacy.backend.types.serialization.file.serializable
import app.meetacy.backend.types.serialization.location.serializable
import app.meetacy.backend.types.serialization.user.serializable
import app.meetacy.backend.types.user.Relationship
import app.meetacy.backend.usecase.types.UsecaseRelationship
import app.meetacy.backend.usecase.types.UserView
import app.meetacy.backend.endpoint.types.UserLocationSnapshot as EndpointUserLocationSnapshot
import app.meetacy.backend.usecase.types.UserLocationSnapshot as UsecaseUserLocationSnapshot

fun UserView.mapToEndpoint() = User(
    isSelf = isSelf,
    relationship = relationship?.mapToEndpoint()?.serializable(),
    id = identity.serializable(),
    nickname = nickname,
    email = email,
    emailVerified = emailVerified,
    username = username?.serializable(),
    avatarId = avatarIdentity?.serializable()
)

fun UsecaseUserLocationSnapshot.mapToEndpoint() = EndpointUserLocationSnapshot(
    user = user.mapToEndpoint(),
    location = location.serializable(),
    capturedAt = capturedAt.serializable()
)

fun User.mapToUsecase(): UserView = UserView(
    isSelf = isSelf,
    relationship = relationship?.type()?.mapToUsecase(),
    identity = id.type(),
    nickname = nickname,
    email = email,
    emailVerified = emailVerified,
    username = username?.type(),
    avatarIdentity = avatarId?.type()
)

fun EndpointUserLocationSnapshot.mapToUsecase() = UsecaseUserLocationSnapshot(
    user = user.mapToUsecase(),
    location = location.type(),
    capturedAt = capturedAt.type()
)

fun UsecaseRelationship.mapToEndpoint(): Relationship = when (this) {
    UsecaseRelationship.Friend -> Relationship.Friend
    UsecaseRelationship.None -> Relationship.None
    UsecaseRelationship.Subscribed -> Relationship.Subscribed
    UsecaseRelationship.Subscriber -> Relationship.Subscriber
}

fun Relationship.mapToUsecase(): UsecaseRelationship = when (this) {
    Relationship.Friend -> UsecaseRelationship.Friend
    Relationship.None -> UsecaseRelationship.None
    Relationship.Subscribed -> UsecaseRelationship.Subscribed
    Relationship.Subscriber -> UsecaseRelationship.Subscriber
}
