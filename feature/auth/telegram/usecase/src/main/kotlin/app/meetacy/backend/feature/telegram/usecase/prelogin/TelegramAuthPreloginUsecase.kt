package app.meetacy.backend.feature.telegram.usecase.prelogin

import app.meetacy.backend.types.access.AccessToken
import app.meetacy.backend.types.auth.telegram.TemporaryTelegramHash
import app.meetacy.backend.types.generator.AccessHashGenerator

class TelegramAuthPreloginUsecase(
    private val storage: Storage,
    private val hashGenerator: HashGenerator,
    private val linkProvider: LinkProvider
) {

    data class Result(
        val temporalToken: AccessToken,
        val telegramLink: String
    )

    suspend fun prelogin(): Result {
        val token = hashGenerator.generateToken()
        val hash = hashGenerator.generateTelegramHash()
        storage.saveTemporalHash(token, hash)
        val link = linkProvider.link(hash)
        return Result(token, link)
    }

    interface HashGenerator {
        fun generateTelegramHash(): TemporaryTelegramHash
        fun generateToken(): AccessToken
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
