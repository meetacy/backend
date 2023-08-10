package app.meetacy.backend.infrastructure.database.tokenGenerator

import app.meetacy.backend.database.integration.tokenGenerator.DatabaseGenerateTokenStorage
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.feature.auth.usecase.auth.GenerateTokenUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.generateTokenStorage: app.meetacy.backend.feature.auth.usecase.auth.GenerateTokenUsecase.Storage by Dependency

fun DIBuilder.tokenGenerator() {
    val generateTokenStorage by singleton<app.meetacy.backend.feature.auth.usecase.auth.GenerateTokenUsecase.Storage> {
        DatabaseGenerateTokenStorage(hashGenerator = get(), database)
    }
}
