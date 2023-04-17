@file:OptIn(ExperimentalCoroutinesApi::class)

import app.meetacy.backend.hash.HashGenerator
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
}
