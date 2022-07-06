package app.meetacy.backend.endpoint.auth

import app.meetacy.backend.endpoint.Credentials
import app.meetacy.backend.endpoint.Status

object MockAuthProvider {
    fun authorize(credentials: Credentials): Status =
        Status(status = credentials.login == "admin" && credentials.password == "admin")
}
