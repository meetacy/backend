package app.meetacy.backend.feature.email.endpoints.integration.confirm

import app.meetacy.backend.feature.email.endpoints.confirm.ConfirmEmailRepository
import app.meetacy.backend.feature.email.endpoints.confirm.ConfirmHashResult
import app.meetacy.backend.feature.email.endpoints.confirm.confirmEmail
import app.meetacy.backend.feature.email.usecase.ConfirmEmailUsecase
import app.meetacy.di.DI
import io.ktor.server.routing.*

internal fun Route.confirmEmail(di: DI) {
    val confirmEmailUsecase: ConfirmEmailUsecase by di.getting
    
    val confirmEmailRepository = object : ConfirmEmailRepository {
        override suspend fun checkConfirmHash(
            email: String,
            confirmHash: String
        ): ConfirmHashResult = when (confirmEmailUsecase.confirm(email, confirmHash)) {
            ConfirmEmailUsecase.ConfirmResult.LinkInvalid ->
                ConfirmHashResult.LinkInvalid
            ConfirmEmailUsecase.ConfirmResult.Success ->
                ConfirmHashResult.Success
        }
    }

    confirmEmail(confirmEmailRepository)
}
