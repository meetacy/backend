package app.meetacy.backend.usecase.integration.email.confirm

import app.meetacy.backend.feature.email.endpoints.confirm.ConfirmEmailRepository
import app.meetacy.backend.feature.email.endpoints.confirm.ConfirmHashResult
import app.meetacy.backend.usecase.email.ConfirmEmailUsecase

class UsecaseConfirmEmailRepository(
    private val usecase: ConfirmEmailUsecase
) : ConfirmEmailRepository {
    override suspend fun checkConfirmHash(email: String, confirmHash: String): ConfirmHashResult =
        when (usecase.confirm(email, confirmHash)) {
            ConfirmEmailUsecase.ConfirmResult.LinkInvalid ->
                ConfirmHashResult.LinkInvalid
            ConfirmEmailUsecase.ConfirmResult.Success ->
                ConfirmHashResult.Success
        }
}
