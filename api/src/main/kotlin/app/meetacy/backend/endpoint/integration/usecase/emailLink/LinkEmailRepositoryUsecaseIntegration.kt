package app.meetacy.backend.endpoint.integration.usecase.emailLink

import app.meetacy.backend.endpoint.auth.email.link.ConfirmHashResult
import app.meetacy.backend.endpoint.auth.email.link.LinkEmailRepository
import app.meetacy.backend.usecase.email.LinkEmailUsecase

class LinkEmailRepositoryUsecaseIntegration(private val usecase: LinkEmailUsecase) : LinkEmailRepository {
    override suspend fun linkEmail(token: String, email: String): ConfirmHashResult =
        when (usecase.linkEmail(email, token)) {
            LinkEmailUsecase.LinkResult.Success -> ConfirmHashResult.Success
            LinkEmailUsecase.LinkResult.TokenInvalid -> ConfirmHashResult.TokenInvalid
        }
}
