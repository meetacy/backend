package app.meetacy.backend.infrastructure.usecase.auth.tokenGenerator

import app.meetacy.backend.feature.auth.endpoints.generate.TokenGenerateRepository
import app.meetacy.backend.infrastructure.database.tokenGenerator.generateTokenStorage
import app.meetacy.backend.types.integration.utf8Checker.DefaultUtf8Checker
import app.meetacy.backend.usecase.auth.GenerateTokenUsecase
import app.meetacy.backend.usecase.integration.auth.UsecaseTokenGenerateRepository
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.tokenGenerateRepository: TokenGenerateRepository by Dependency

fun DIBuilder.tokenGenerator() {
    val tokenGenerateRepository by singleton<TokenGenerateRepository> {
        UsecaseTokenGenerateRepository(
            usecase = GenerateTokenUsecase(
                storage = generateTokenStorage,
                tokenGenerator = get(),
                utf8Checker = DefaultUtf8Checker
            )
        )
    }
}
