package app.meetacy.backend.endpoint.auth

import app.meetacy.backend.endpoint.Credentials
import app.meetacy.backend.endpoint.Status

interface AuthProvider {
    suspend fun authorize(credentials: Credentials): Status
}
