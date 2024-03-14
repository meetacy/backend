package app.meetacy.backend.feature.friends.usecase.integration

import app.meetacy.backend.feature.friends.usecase.integration.add.addFriendUsecase
import app.meetacy.backend.feature.friends.usecase.integration.delete.deleteFriendUsecase
import app.meetacy.backend.feature.friends.usecase.integration.list.listFriendsUsecase
import app.meetacy.backend.feature.friends.usecase.integration.location.locationMiddleware
import app.meetacy.backend.feature.friends.usecase.integration.location.stream.locationStreamingUsecase
import app.meetacy.backend.feature.friends.usecase.integration.subscribers.getSubscribersUsecase
import app.meetacy.backend.feature.friends.usecase.integration.subscriptions.getSubscriptionsUsecase
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.friends() {
    addFriendUsecase()
    deleteFriendUsecase()
    listFriendsUsecase()
    locationMiddleware()
    getSubscribersUsecase()
    getSubscriptionsUsecase()
    locationStreamingUsecase()
    pushLocationUsecase()
    locationMiddleware()
}
