@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.auth.tokenGenerator

import app.meetacy.backend.database.integration.tokenGenerator.DatabaseGenerateTokenStorage
import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.di.dependency.Dependency
import app.meetacy.backend.endpoint.auth.generate.TokenGenerateRepository
import app.meetacy.backend.hash.integration.DefaultHashGenerator
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.usecase.auth.GenerateTokenUsecase
import app.meetacy.backend.usecase.integration.auth.UsecaseTokenGenerateRepository
import app.meetacy.backend.utf8.integration.DefaultUtf8Checker

val DI.tokenGenerateRepository: TokenGenerateRepository by Dependency

fun DIBuilder.tokenGenerator() {
    val tokenGenerateRepository by singleton<TokenGenerateRepository> {
        UsecaseTokenGenerateRepository(
            usecase = GenerateTokenUsecase(
                storage = DatabaseGenerateTokenStorage(DefaultHashGenerator, database),
                tokenGenerator = DefaultHashGenerator,
                utf8Checker = DefaultUtf8Checker
            )
        )
    }
}