package app.meetacy.backend.feature.telegram.usecase

import app.meetacy.backend.types.access.AccessToken
import app.meetacy.backend.types.generator.AccessHashGenerator
import app.meetacy.backend.types.prelogin.TemporaryTelegramHash


class GenerateTelegramTemporaryTokenUsecase(
    private val storage: Storage,
    private val telegramHashGenerator: HashGenerator,
    private val linkProvider: LinkProvider,
    private val tokenGenerator: AccessHashGenerator
) {

    suspend fun generateToken(): Result {
        val token = AccessToken(tokenGenerator.generate())
        val hash = telegramHashGenerator.generate()
        storage.saveTemporalToken(token, hash)
        val link = linkProvider.link(hash)
        return Result(token, link)
    }

    interface HashGenerator {
        fun generate(): TemporaryTelegramHash
    }

    interface LinkProvider {
        fun link(token: TemporaryTelegramHash): String
    }

    data class Result(
        val temporalToken: AccessToken,
        val telegramLink: String
    )

    interface Storage {
        suspend fun saveTemporalToken(
            temporalToken: AccessToken,
            telegramHash: TemporaryTelegramHash
        )
    }
}
