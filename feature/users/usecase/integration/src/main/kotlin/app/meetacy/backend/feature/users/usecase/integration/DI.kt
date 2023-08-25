package app.meetacy.backend.feature.users.usecase.integration

import app.meetacy.backend.feature.users.usecase.integration.edit.editUser
import app.meetacy.backend.feature.users.usecase.integration.get.getUser
import app.meetacy.backend.feature.users.usecase.integration.get.viewUser
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.users() {
    editUser()
    getUser()
    viewUser()
}
