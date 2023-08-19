package app.meetacy.backend.application.usecase.users

import app.meetacy.backend.infrastructure.usecase.users.edit.editUserRepository
import app.meetacy.backend.infrastructure.usecase.users.get.getUserRepository
import app.meetacy.backend.infrastructure.usecase.users.validate.validateUsernameRepository
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.users() {
    getUserRepository()
    editUserRepository()
    validateUsernameRepository()
}
