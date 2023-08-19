package app.meetacy.backend.feature.email.endpoints.integration.link

import app.meetacy.backend.feature.email.endpoints.link.ConfirmHashResult
import app.meetacy.backend.feature.email.endpoints.link.LinkEmailRepository
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.feature.email.usecase.LinkEmailUsecase
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.linkEmailRepository() {
    val linkEmailRepository by singleton<LinkEmailRepository> {
        val usecase: LinkEmailUsecase by getting

        object : LinkEmailRepository {
            override suspend fun linkEmail(
                token: AccessIdentity,
                email: String
            ): ConfirmHashResult =
                when (usecase.linkEmail(email, token.type())) {
                    LinkEmailUsecase.LinkResult.Success -> ConfirmHashResult.Success
                    LinkEmailUsecase.LinkResult.TokenInvalid -> ConfirmHashResult.InvalidIdentity
                }
        }
    }
}
