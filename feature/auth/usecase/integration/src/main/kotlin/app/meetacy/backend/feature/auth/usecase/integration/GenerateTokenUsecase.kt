package app.meetacy.backend.feature.auth.usecase.integration

import app.meetacy.backend.constants.ACCESS_TOKEN_LENGTH
import app.meetacy.backend.feature.auth.database.TokensStorage
import app.meetacy.backend.feature.auth.usecase.GenerateTokenUsecase
import app.meetacy.backend.hash.HashGenerator
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.access.AccessToken
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.generateTokenUsecase() {
    val generateTokenUsecase by singleton {
        val tokensStorage: TokensStorage by getting

        val tokenGenerator = GenerateTokenUsecase.TokenGenerator {
            HashGenerator
                .generate(ACCESS_TOKEN_LENGTH)
                .let(::AccessToken)
        }

        val storage = object : GenerateTokenUsecase.Storage {
            override suspend fun addToken(accessIdentity: AccessIdentity) {
                tokensStorage.addToken(accessIdentity)
            }
        }

        GenerateTokenUsecase(storage, tokenGenerator)
    }
}
