package app.meetacy.backend.feature.auth.usecase.integration

import app.meetacy.backend.feature.auth.database.TokensStorage
import app.meetacy.backend.feature.auth.usecase.CreateUserUsecase
import app.meetacy.backend.feature.auth.usecase.GenerateTokenUsecase
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.users.UserId
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.generateTokenUsecase() {
    val generateTokenUsecase by singleton {
        GenerateTokenUsecase(
            storage = DatabaseGenerateTokenStorage(
                tokensStorage = get(),
                createUserUsecase = get()
            ),
            tokenGenerator = get(),
            utf8Checker = get()
        )
    }
}

private class DatabaseGenerateTokenStorage(
    private val tokensStorage: TokensStorage,
    private val createUserUsecase: CreateUserUsecase
) : GenerateTokenUsecase.Storage {

    override suspend fun createUser(nickname: String): UserId =
        createUserUsecase.createUser(nickname)

    override suspend fun addToken(accessIdentity: AccessIdentity) =
        tokensStorage.addToken(accessIdentity)
}
