package app.meetacy.backend.usecase.invitations.accept

import app.meetacy.backend.usecase.types.AuthRepository

class AcceptInvitationUsecase(
    private val authRepository: AuthRepository,
    private val storage: Storage
) {
    sealed interface Result {
        object Success: Result
        object NotFound: Result
        object Unauthorized: Result
    }

    // TODO

    interface Storage {

    }
}