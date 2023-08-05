@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.auth.tokenGenerator

import app.meetacy.backend.endpoint.auth.generate.TokenGenerateRepository
import app.meetacy.backend.infrastructure.database.tokenGenerator.generateTokenStorage
import app.meetacy.backend.usecase.auth.GenerateTokenUsecase
import app.meetacy.backend.usecase.integration.auth.UsecaseTokenGenerateRepository
import app.meetacy.backend.utf8.integration.DefaultUtf8Checker
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