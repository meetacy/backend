package app.meetacy.backend.infrastructure.integrations.auth

import app.meetacy.backend.endpoint.auth.AuthDependencies
import app.meetacy.backend.infrastructure.integrations.auth.email.emailDependencies
import app.meetacy.backend.infrastructure.integrations.auth.tokenGenerator.tokenGenerateRepository
import org.jetbrains.exposed.sql.Database

fun authDependenciesFactory(
    db: Database
): AuthDependencies = AuthDependencies(
    emailDependencies = emailDependencies(db),
    tokenGenerateRepository = tokenGenerateRepository(db)
)
