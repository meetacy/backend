package app.meetacy.backend.feature.telegram.usecase.prelogin

import app.meetacy.backend.types.access.AccessToken
import app.meetacy.backend.types.auth.telegram.TemporaryTelegramHash
import app.meetacy.backend.types.generator.AccessHashGenerator

class TelegramAuthPreloginUsecase(
    private val storage: Storage,
    private val telegramHashGenerator: HashGenerator,
    private val linkProvider: LinkProvider,
    private val tokenGenerator: AccessHashGenerator
) {

    data class Result(
        val temporalToken: AccessToken,
        val telegramLink: String
    )

    suspend fun prelogin(): Result {
        val token = AccessToken(tokenGenerator.generate())
        val hash = telegramHashGenerator.generate()
        storage.saveTemporalHash(token, hash)
        val link = linkProvider.link(hash)
        return Result(token, link)
    }

    interface HashGenerator {
        fun generate(): TemporaryTelegramHash
    }

    interface LinkProvider {
        fun link(hash: TemporaryTelegramHash): String
    }

    interface Storage {
        suspend fun saveTemporalHash(
            temporalToken: AccessToken,
            hash: TemporaryTelegramHash
        )
    }
}
