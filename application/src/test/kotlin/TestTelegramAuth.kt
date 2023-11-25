@file:OptIn(UnstableApi::class)

import app.meetacy.sdk.types.annotation.UnstableApi
import app.meetacy.sdk.types.auth.telegram.SecretTelegramBotKey
import app.meetacy.sdk.types.auth.telegram.TemporalTelegramHash
import kotlinx.coroutines.async
import org.junit.jupiter.api.Test

class TestTelegramAuth {

    @Test
    fun `test telegram auth`() = runTestServer {
        val firstTelegramId = 0L
        val secondTelegramId = 1L
        val username = "username"
        val firstName = "Name"
        val lastName = "Surname"
        val newUsername = "new_username"
        val newFirstName = "new Name"
        val newLastName = "new Surname"

        val firstTempAuth = testApi.auth.telegram.prelogin()
        val firstTempHash = TemporalTelegramHash(firstTempAuth.botLink.string)
        val async = async { firstTempAuth.await() }
        testApi.auth.telegram.finish(
            temporalHash = firstTempHash,
            secretBotKey = SecretTelegramBotKey(""),
            telegramId = firstTelegramId,
            username = username,
            firstName = firstName,
            lastName = lastName,
        )

        val firstUser = async.await().getMe()
        require(firstUser.username?.string == username)
        require(firstUser.nickname == "$firstName $lastName")

        val repeatedTempAuth = testApi.auth.telegram.prelogin()
        val repeatedTempHash = TemporalTelegramHash(repeatedTempAuth.botLink.string)
        testApi.auth.telegram.finish(
            temporalHash = repeatedTempHash,
            secretBotKey = SecretTelegramBotKey(""),
            telegramId = firstTelegramId,
            username = newUsername,
            firstName = newFirstName,
            lastName = newLastName,
        )

        val repeatedUser = repeatedTempAuth.await().getMe()
        require(repeatedUser.id == firstUser.id)
        require(repeatedUser.username?.string == username)
        require(repeatedUser.nickname == "$firstName $lastName")


        val secondTempAuth = testApi.auth.telegram.prelogin()
        val secondTempHash = TemporalTelegramHash(secondTempAuth.botLink.string)
        testApi.auth.telegram.finish(
            temporalHash = secondTempHash,
            secretBotKey = SecretTelegramBotKey(""),
            telegramId = secondTelegramId,
            username = username,
            firstName = firstName,
            lastName = lastName,
        )

        val secondUser = secondTempAuth.await().getMe()
        require(secondUser.username == null)
        require(secondUser.nickname == "$firstName $lastName")
    }
}
