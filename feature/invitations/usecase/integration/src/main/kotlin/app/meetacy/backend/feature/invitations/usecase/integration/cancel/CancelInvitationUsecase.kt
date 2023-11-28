package app.meetacy.backend.feature.invitations.usecase.integration.cancel

import app.meetacy.backend.feature.invitations.database.invitations.InvitationsStorage
import app.meetacy.backend.feature.invitations.usecase.cancel.CancelInvitationUsecase
import app.meetacy.backend.feature.invitations.usecase.integration.types.mapToUsecase
import app.meetacy.backend.types.invitation.FullInvitation
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.cancelInvitation() {
    val cancelInvitationUsecase by singleton<CancelInvitationUsecase> {
        val authRepository: AuthRepository by getting
        val storage = object : CancelInvitationUsecase.Storage {
            private val invitationsStorage: InvitationsStorage by getting

            override suspend fun cancel(id: InvitationId): Boolean =
                invitationsStorage.cancel(id)

            override suspend fun getInvitation(id: InvitationId): FullInvitation? =
                invitationsStorage.getInvitationsOrNull(listOf(id)).singleOrNull()?.mapToUsecase()
        }
        CancelInvitationUsecase(authRepository, storage)
    }
}
