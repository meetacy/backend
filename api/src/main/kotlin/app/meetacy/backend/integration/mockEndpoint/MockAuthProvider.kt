package app.meetacy.backend.integration.mockEndpoint

import app.meetacy.backend.endpoint.Credentials
import app.meetacy.backend.endpoint.Status
import app.meetacy.backend.endpoint.auth.AuthProvider
import app.meetacy.backend.mock.MockAuth

object MockAuthProvider : AuthProvider {
    override suspend fun authorize(credentials: Credentials): Status {
        val status = MockAuth.authorize(credentials.login, credentials.password)
        return Status(status)
    }
}
