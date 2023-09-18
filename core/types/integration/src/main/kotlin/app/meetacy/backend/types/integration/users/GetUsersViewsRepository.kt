package app.meetacy.backend.types.integration.users

import app.meetacy.backend.feature.users.usecase.get.GetUsersViewsUsecase
import app.meetacy.backend.types.users.GetUsersViewsRepository
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.getUsersViewsRepository() {
    val getUsersViewsRepository by singleton<GetUsersViewsRepository> {
        val getUsersViewsUsecase: GetUsersViewsUsecase by getting

        GetUsersViewsRepository { viewerId, userIdentities ->
            getUsersViewsUsecase.viewUsers(viewerId, userIdentities)
        }
    }
}
