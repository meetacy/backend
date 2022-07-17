package app.meetacy.backend.usecase.integration.emailConfirm

import app.meetacy.backend.endpoint.auth.email.confirm.ConfirmHashResult
import app.meetacy.backend.endpoint.auth.email.confirm.ConfirmEmailRepository
import app.meetacy.backend.usecase.email.ConfirmEmailUsecase

private class Integration(
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

fun usecaseConfirmEmailRepository(usecase: ConfirmEmailUsecase): ConfirmEmailRepository =
    Integration(usecase)
