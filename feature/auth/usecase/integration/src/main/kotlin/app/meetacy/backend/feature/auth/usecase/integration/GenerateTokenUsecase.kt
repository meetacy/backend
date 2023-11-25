package app.meetacy.backend.feature.auth.usecase.integration

import app.meetacy.backend.feature.auth.database.TokensStorage
import app.meetacy.backend.feature.auth.usecase.GenerateTokenUsecase
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.generator.AccessHashGenerator
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.generateTokenUsecase() {
    val generateTokenUsecase by singleton {
        val accessHashGenerator: AccessHashGenerator by getting
        val tokensStorage: TokensStorage by getting

        val storage = object : GenerateTokenUsecase.Storage {
            override suspend fun addToken(accessIdentity: AccessIdentity) {
                tokensStorage.addToken(accessIdentity)
            }
        }

        GenerateTokenUsecase(storage, accessHashGenerator)
    }
}
