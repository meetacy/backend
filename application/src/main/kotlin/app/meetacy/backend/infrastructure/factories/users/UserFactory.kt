package app.meetacy.backend.infrastructure.factories.users

import app.meetacy.backend.endpoint.users.UsersDependencies
import app.meetacy.backend.infrastructure.factories.users.edit.editUserRepository
import app.meetacy.backend.infrastructure.factories.users.get.getUserRepository
import org.jetbrains.exposed.sql.Database

fun userDependenciesFactory(
    db: Database
): UsersDependencies = UsersDependencies(
    getUserRepository = getUserRepository(db),
    editUserRepository = editUserRepository(db)
)
