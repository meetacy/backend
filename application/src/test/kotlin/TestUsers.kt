import app.meetacy.backend.hash.HashGenerator
import app.meetacy.sdk.exception.MeetacyUserNotFoundException
import app.meetacy.sdk.exception.MeetacyUsernameAlreadyOccupiedException
import app.meetacy.sdk.types.amount.amountOrZero
import app.meetacy.sdk.types.optional.Optional
import app.meetacy.sdk.types.user.username
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test
import kotlin.test.assertEquals

class TestUsers {
    @Test
    fun `test if user can be edited`() = runTestServer {
        val user = generateTestAccount()

        val newNickname = HashGenerator.generate((1..100).random())

        val editedUser = user.edited(
            nickname = Optional.Present(newNickname)
        )

        require(editedUser.nickname == newNickname)

        val updatedUser = editedUser.details()

        require(updatedUser.nickname == newNickname)
    }

    @Test
    fun `test usernames edit`() = runTestServer {
        val user = generateTestAccount()

        val newUsername = ("username_" + HashGenerator.generate((1..20).random()).take(10)).username

        val editedUser = user.edited(username = Optional.Present(newUsername))

        require(editedUser.username == newUsername)

        val updatedUser = editedUser.details()

        require(updatedUser.username == newUsername)
    }

    @Test
    fun `test followers`() = runTestServer {
        val user = generateTestAccount()

        require(user.subscribersAmount == 0.amountOrZero)
        require(user.subscriptionsAmount == 0.amountOrZero)

        val subscriber = generateTestAccount(postfix = "subscriber")
        subscriber.friends.add(user.id)

        val updatedUser = user.updated()
        val updatedSubscriber = subscriber.updated()

        require(updatedUser.subscribersAmount == 1.amountOrZero)
        require(updatedSubscriber.subscriptionsAmount == 1.amountOrZero)
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

    @Test
    fun `test if can get user by username`() = runTestServer {
        val username = ("username_" + HashGenerator.generate((1..20).random()).take(10)).username

        val self = generateTestAccount()
        val other = generateTestAccount(postfix = "Other")

        assertThrows<MeetacyUserNotFoundException> { self.users.get(username) }
        assertThrows<MeetacyUserNotFoundException> { other.users.get(username) }

        val updated = self.edited(username = Optional.Present(username)).details()
        val resolved = self.users.get(username)

        require(updated.data == resolved.data)

        val resolvedByOtherById = other.users.get(self.id)
        val resolvedByOtherByUsername = other.users.get(username)
        require(resolvedByOtherById.data == resolvedByOtherByUsername.data)
    }
}
