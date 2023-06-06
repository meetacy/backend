@file:OptIn(ExperimentalCoroutinesApi::class)

import app.meetacy.backend.hash.HashGenerator
import app.meetacy.sdk.types.user.username
import app.meetacy.sdk.types.optional.Optional
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test

class TestUsers {
    @Test
    fun `test if user can be edited`() = runTestServer {
        val user = generateTestAccount()

        val newNickname = HashGenerator.generate()

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

        val newUsername = ("username_" + HashGenerator.generate().take(10)).username

        val editedUser = user.edited(username = Optional.Present(newUsername))

        require(editedUser.username == newUsername)

        val updatedUser = editedUser.updated()

        require(updatedUser.username == newUsername)
    }
}
