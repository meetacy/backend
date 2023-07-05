package app.meetacy.backend.infrastructure.integrations.users

import app.meetacy.backend.endpoint.users.UsersDependencies
import app.meetacy.backend.infrastructure.integrations.users.edit.editUserRepository
import app.meetacy.backend.infrastructure.integrations.users.get.getUserRepository
import org.jetbrains.exposed.sql.Database

fun userDependenciesFactory(
    db: Database
): UsersDependencies = UsersDependencies(
    getUserRepository = getUserRepository(db),
    editUserRepository = editUserRepository(db)
)
