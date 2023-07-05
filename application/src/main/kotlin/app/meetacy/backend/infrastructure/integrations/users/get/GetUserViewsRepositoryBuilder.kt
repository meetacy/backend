package app.meetacy.backend.infrastructure.integrations.users.get

import app.meetacy.backend.database.integration.types.DatabaseGetUsersViewsRepository
import app.meetacy.backend.usecase.types.GetUsersViewsRepository
import org.jetbrains.exposed.sql.Database

fun getUserViewsRepository(db: Database): GetUsersViewsRepository = DatabaseGetUsersViewsRepository(db)
