package app.meetacy.backend.endpoint.integration.usecase.emailConfirm

import app.meetacy.backend.endpoint.auth.email.confirm.ConfirmHashResult
import app.meetacy.backend.endpoint.auth.email.confirm.ConfirmStorage
import app.meetacy.backend.usecase.email.ConfirmEmailUsecase

class ConfirmStorageUsecaseIntegration(
    private val usecase: ConfirmEmailUsecase
) : ConfirmStorage {
    override suspend fun checkConfirmHash(email: String, confirmHash: String): ConfirmHashResult =
        when (usecase.confirm(email, confirmHash)) {
            ConfirmEmailUsecase.ConfirmResult.LinkInvalid ->
                ConfirmHashResult.LinkInvalid
            ConfirmEmailUsecase.ConfirmResult.Success ->
                ConfirmHashResult.Success
        }
}
