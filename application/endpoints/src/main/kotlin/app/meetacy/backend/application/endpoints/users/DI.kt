package app.meetacy.backend.application.endpoints.users

import app.meetacy.backend.application.usecase.users.edit.editUserRepository
import app.meetacy.backend.application.usecase.users.get.getUserRepository
import app.meetacy.backend.application.usecase.users.validate.validateUsernameRepository
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.users() {
    getUserRepository()
    editUserRepository()
    validateUsernameRepository()
}
