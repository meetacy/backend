package app.meetacy.backend.feature.email.usecase.integration.link

import app.meetacy.backend.feature.email.endpoints.link.ConfirmHashResult
import app.meetacy.backend.feature.email.endpoints.link.LinkEmailRepository
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.feature.email.usecase.LinkEmailUsecase

class UsecaseLinkEmailRepository(private val usecase: LinkEmailUsecase) : LinkEmailRepository {
    override suspend fun linkEmail(
        token: AccessIdentity,
        email: String
    ): ConfirmHashResult =
        when (usecase.linkEmail(email, token.type())) {
            LinkEmailUsecase.LinkResult.Success -> ConfirmHashResult.Success
            LinkEmailUsecase.LinkResult.TokenInvalid -> ConfirmHashResult.InvalidIdentity
        }
}
