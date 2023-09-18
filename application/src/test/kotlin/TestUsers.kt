@file:OptIn(ExperimentalCoroutinesApi::class)

import app.meetacy.backend.hash.HashGenerator
import app.meetacy.sdk.exception.MeetacyUsernameAlreadyOccupiedException
import app.meetacy.sdk.types.optional.Optional
import app.meetacy.sdk.types.user.username
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test

class TestUsers {
    @Test
    fun `test if user can be edited`() = runTestServer {
        val user = generateTestAccount()

        val newNickname = HashGenerator.generate((1..100).random())

        val editedUser = user.edited(
            nickname = Optional.Present(newNickname)
        )

        require(editedUser.nickname == newNickname)

        val updatedUser = editedUser.updated()

        require(updatedUser.nickname == newNickname)
    }

    @Test
    fun `test usernames edit`() = runTestServer {
        val user = generateTestAccount()

        val newUsername = ("username_" + HashGenerator.generate((1..20).random()).take(10)).username

        val editedUser = user.edited(username = Optional.Present(newUsername))

        require(editedUser.username == newUsername)

        val updatedUser = editedUser.updated()

        require(updatedUser.username == newUsername)
    }

    @Test
    fun `existing username test`() = runTestServer {
        val username = ("username_" + HashGenerator.generate((1..20).random()).take(10)).username

        generateTestAccount()
            .edited(username = Optional.Present(username))

        assertThrows<MeetacyUsernameAlreadyOccupiedException> {
            generateTestAccount(postfix = "#2")
                .edited(username = Optional.Present(username))
        }
    }
}
