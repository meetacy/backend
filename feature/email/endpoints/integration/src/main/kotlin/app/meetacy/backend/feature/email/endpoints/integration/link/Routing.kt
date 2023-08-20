package app.meetacy.backend.feature.email.endpoints.integration.link

import app.meetacy.backend.feature.email.endpoints.link.LinkEmailRepository
import app.meetacy.backend.feature.email.endpoints.link.linkEmail
import app.meetacy.di.global.di
import io.ktor.server.routing.*

internal fun Route.linkEmail() {
    val linkEmailRepository: LinkEmailRepository by di.getting
    linkEmail(linkEmailRepository)
}
