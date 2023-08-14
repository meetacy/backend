package app.meetacy.backend.infrastructure.database.users.get

import app.meetacy.backend.feature.auth.database.integration.types.DatabaseGetUsersViewsRepository
import app.meetacy.backend.feature.auth.usecase.types.GetUsersViewsRepository
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.getUserViewsRepository: GetUsersViewsRepository by Dependency

fun DIBuilder.getUser() {
    val getUserViewsRepository by singleton<GetUsersViewsRepository> {
        DatabaseGetUsersViewsRepository(database)
    }
}
