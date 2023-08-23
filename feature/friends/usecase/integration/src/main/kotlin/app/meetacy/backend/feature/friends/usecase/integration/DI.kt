package app.meetacy.backend.feature.friends.usecase.integration

import app.meetacy.di.builder.DIBuilder

fun DIBuilder.friends() {
    addFriendUsecase()
    deleteFriendUsecase()
    listFriendsUsecase()
    locationMiddleware()
    locationStreamingUsecase()
}
