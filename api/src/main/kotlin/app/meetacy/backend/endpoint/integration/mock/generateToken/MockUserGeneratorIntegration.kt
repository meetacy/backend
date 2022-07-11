package app.meetacy.backend.endpoint.integration.mock.generateToken

import app.meetacy.backend.mock.storage.MockUser
import app.meetacy.backend.mock.storage.UsersStorage

interface AccessHashGenerator {
    fun generateAccessHash(): String
}

class MockUserGeneratorIntegration(
    private val generator: AccessHashGenerator
) : UserGenerator {
    override fun generateUser(nickname: String): MockUser {
        return UsersStorage.addUser(
            accessHash = generator.generateAccessHash(),
            nickname = nickname
        )
    }
}
