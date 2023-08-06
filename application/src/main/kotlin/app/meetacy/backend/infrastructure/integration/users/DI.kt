package app.meetacy.backend.infrastructure.integration.users

import app.meetacy.backend.infrastructure.integration.users.edit.editUserRepository
import app.meetacy.backend.infrastructure.integration.users.get.getUserRepository
import app.meetacy.backend.infrastructure.integration.users.validate.validateUsernameRepository
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.users() {
    getUserRepository()
    editUserRepository()
    validateUsernameRepository()
}
