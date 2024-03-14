package app.meetacy.backend.feature.friends.usecase.integration

import app.meetacy.backend.feature.friends.usecase.integration.add.addFriendUsecase
import app.meetacy.backend.feature.friends.usecase.integration.delete.deleteFriendUsecase
import app.meetacy.backend.feature.friends.usecase.integration.list.listFriendsUsecase
import app.meetacy.backend.feature.friends.usecase.integration.location.locationMiddleware
import app.meetacy.backend.feature.friends.usecase.integration.location.push.pushLocationUsecase
import app.meetacy.backend.feature.friends.usecase.integration.location.stream.locationStreamingUsecase
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.friends() {
    addFriendUsecase()
    deleteFriendUsecase()
    listFriendsUsecase()
    locationStreamingUsecase()
    pushLocationUsecase()
    locationMiddleware()
}
