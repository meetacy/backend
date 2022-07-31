package app.meetacy.backend.usecase.integration.emailLink

import app.meetacy.backend.domain.AccessToken
import app.meetacy.backend.endpoint.auth.email.link.ConfirmHashResult
import app.meetacy.backend.endpoint.auth.email.link.LinkEmailRepository
import app.meetacy.backend.usecase.email.LinkEmailUsecase

private class Integration(
    private val usecase: LinkEmailUsecase
) : LinkEmailRepository {
    override suspend fun linkEmail(token: AccessToken, email: String): ConfirmHashResult =
        when (usecase.linkEmail(email, token)) {
            LinkEmailUsecase.LinkResult.Success -> ConfirmHashResult.Success
            LinkEmailUsecase.LinkResult.TokenInvalid -> ConfirmHashResult.TokenInvalid
        }
}

fun usecaseLinkEmailRepository(usecase: LinkEmailUsecase): LinkEmailRepository =
    Integration(usecase)
