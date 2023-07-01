package app.meetacy.backend.infrastructure.factories.auth

import app.meetacy.backend.database.integration.tokenGenerator.DatabaseGenerateTokenStorage
import app.meetacy.backend.endpoint.auth.AuthDependencies
import app.meetacy.backend.hash.integration.DefaultHashGenerator
import app.meetacy.backend.infrastructure.factories.auth.email.emailDependencies
import app.meetacy.backend.usecase.auth.GenerateTokenUsecase
import app.meetacy.backend.usecase.integration.auth.UsecaseTokenGenerateRepository
import app.meetacy.backend.utf8.integration.DefaultUtf8Checker
import org.jetbrains.exposed.sql.Database

fun authDependenciesFactory(
    db: Database
): AuthDependencies = AuthDependencies(
        emailDependencies = emailDependencies(db),
        tokenGenerateRepository = UsecaseTokenGenerateRepository(
            usecase = GenerateTokenUsecase(
                storage = DatabaseGenerateTokenStorage(DefaultHashGenerator, db),
                tokenGenerator = DefaultHashGenerator,
                utf8Checker = DefaultUtf8Checker
            )
        )
    )
