package app.meetacy.backend.feature.users.usecase.integration

import app.meetacy.backend.feature.users.usecase.integration.edit.editUserUsecase
import app.meetacy.backend.feature.users.usecase.integration.get.getUserSafeUsecase
import app.meetacy.backend.feature.users.usecase.integration.get.getUsersViewsUsecase
import app.meetacy.backend.feature.users.usecase.integration.get.viewUserUsecase
import app.meetacy.backend.feature.users.usecase.integration.username.available.usernameAvailableUsecase
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.users() {
    editUserUsecase()
    getUserSafeUsecase()
    getUsersViewsUsecase()
    viewUserUsecase()
    usernameAvailableUsecase()
}
