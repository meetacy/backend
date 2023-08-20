package app.meetacy.backend.feature.email.endpoints.integration.link

import app.meetacy.backend.feature.email.endpoints.link.ConfirmHashResult
import app.meetacy.backend.feature.email.endpoints.link.LinkEmailRepository
import app.meetacy.backend.feature.email.endpoints.link.linkEmail
import app.meetacy.backend.feature.email.usecase.LinkEmailUsecase
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.di.global.di
import io.ktor.server.routing.*

internal fun Route.linkEmail() {
    val linkEmailUsecase: LinkEmailUsecase by di.getting

    val linkEmailRepository = object : LinkEmailRepository {
        override suspend fun linkEmail(
            token: AccessIdentity,
            email: String
        ): ConfirmHashResult = when (linkEmailUsecase.linkEmail(email, token.type())) {
            LinkEmailUsecase.LinkResult.Success -> ConfirmHashResult.Success
            LinkEmailUsecase.LinkResult.TokenInvalid -> ConfirmHashResult.InvalidIdentity
        }
    }

    linkEmail(linkEmailRepository)
}
