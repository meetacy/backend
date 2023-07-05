package app.meetacy.backend.infrastructure.integrations.auth.tokenGenerator

import app.meetacy.backend.database.integration.tokenGenerator.DatabaseGenerateTokenStorage
import app.meetacy.backend.endpoint.auth.generate.TokenGenerateRepository
import app.meetacy.backend.hash.integration.DefaultHashGenerator
import app.meetacy.backend.usecase.auth.GenerateTokenUsecase
import app.meetacy.backend.usecase.integration.auth.UsecaseTokenGenerateRepository
import app.meetacy.backend.utf8.integration.DefaultUtf8Checker
import org.jetbrains.exposed.sql.Database

fun tokenGenerateRepository(db: Database): TokenGenerateRepository = UsecaseTokenGenerateRepository(
    usecase = GenerateTokenUsecase(
        storage = DatabaseGenerateTokenStorage(DefaultHashGenerator, db),
        tokenGenerator = DefaultHashGenerator,
        utf8Checker = DefaultUtf8Checker
    )
)
