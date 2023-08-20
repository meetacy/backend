package app.meetacy.backend.feature.auth.endpoints.integration.generate

import app.meetacy.backend.feature.auth.endpoints.generate.TokenGenerateRepository
import app.meetacy.backend.feature.auth.endpoints.generate.generateToken
import app.meetacy.di.global.di
import io.ktor.server.routing.*

internal fun Route.generateToken() {
    val tokenGenerateRepository: TokenGenerateRepository by di.getting
    generateToken(tokenGenerateRepository)
}
