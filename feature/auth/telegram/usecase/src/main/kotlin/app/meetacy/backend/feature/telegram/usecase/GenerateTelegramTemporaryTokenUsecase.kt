package app.meetacy.backend.feature.telegram.usecase

import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.generator.AccessHashGenerator
import app.meetacy.backend.types.prelogin.TemporaryTelegramHash


class GenerateTelegramTemporaryTokenUsecase(
    private val storage: Storage,
    private val telegramHashGenerator: HashGenerator,
    private val linkProvider: LinkProvider,
    private val accessHashGenerator: AccessHashGenerator
) {

    suspend fun generateToken(): Result {
        val token = AccessHash(accessHashGenerator.generate())
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
        val temporalToken: AccessHash,
        val telegramLink: String
    )

    interface Storage {
        suspend fun saveTemporalToken(
            temporalToken: AccessHash,
            telegramHash: TemporaryTelegramHash
        )
    }
}
