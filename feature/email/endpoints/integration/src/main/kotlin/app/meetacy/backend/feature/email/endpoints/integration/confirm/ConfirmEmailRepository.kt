package app.meetacy.backend.feature.email.endpoints.integration.confirm

import app.meetacy.backend.feature.email.endpoints.confirm.ConfirmEmailRepository
import app.meetacy.backend.feature.email.endpoints.confirm.ConfirmHashResult
import app.meetacy.backend.feature.email.usecase.ConfirmEmailUsecase
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.confirmEmailRepository() {
    val confirmEmailRepository by singleton<ConfirmEmailRepository> {
        val usecase: ConfirmEmailUsecase by getting

        object : ConfirmEmailRepository {
            override suspend fun checkConfirmHash(
                email: String,
                confirmHash: String
            ): ConfirmHashResult = when (usecase.confirm(email, confirmHash)) {
                ConfirmEmailUsecase.ConfirmResult.LinkInvalid ->
                    ConfirmHashResult.LinkInvalid
                ConfirmEmailUsecase.ConfirmResult.Success ->
                    ConfirmHashResult.Success
            }
        }
    }
}

