package app.meetacy.backend.usecase.integration.email.link

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.endpoint.auth.email.link.ConfirmHashResult
import app.meetacy.backend.endpoint.auth.email.link.LinkEmailRepository
import app.meetacy.backend.usecase.email.LinkEmailUsecase

class UsecaseLinkEmailRepository(private val usecase: LinkEmailUsecase) : LinkEmailRepository {
    override suspend fun linkEmail(token: AccessIdentity, email: String): ConfirmHashResult =
        when (usecase.linkEmail(email, token)) {
            LinkEmailUsecase.LinkResult.Success -> ConfirmHashResult.Success
            LinkEmailUsecase.LinkResult.TokenInvalid -> ConfirmHashResult.InvalidIdentity
        }
}
