package app.meetacy.backend.feature.email.endpoints.integration.confirm

import app.meetacy.backend.feature.email.endpoints.confirm.ConfirmEmailRepository
import app.meetacy.backend.feature.email.endpoints.confirm.confirmEmail
import app.meetacy.di.global.di
import io.ktor.server.routing.*

internal fun Route.confirmEmail() {
    val confirmEmailRepository: ConfirmEmailRepository by di.getting
    confirmEmail(confirmEmailRepository)
}
