package app.meetacy.backend.infrastructure.factories.auth

import app.meetacy.backend.database.integration.types.DatabaseAuthRepository
import app.meetacy.backend.usecase.types.AuthRepository
import org.jetbrains.exposed.sql.Database

fun authRepository(db: Database): AuthRepository = DatabaseAuthRepository(db)
