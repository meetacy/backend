package app.meetacy.backend.feature.invitations.usecase.integration.deny

import app.meetacy.backend.feature.invitations.database.invitations.InvitationsStorage
import app.meetacy.backend.feature.invitations.usecase.deny.DenyInvitationUsecase
import app.meetacy.backend.feature.invitations.usecase.integration.types.mapToUsecase
import app.meetacy.backend.types.invitation.FullInvitation
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.denyInvitation() {
    val denyInvitationUsecase by singleton<DenyInvitationUsecase> {
        val authRepository: AuthRepository by getting
        val storage = object : DenyInvitationUsecase.Storage {
            private val invitationsStorage: InvitationsStorage by getting

            override suspend fun getInvitation(id: InvitationId): FullInvitation? =
                invitationsStorage.getInvitationsOrNull(listOf(id)).singleOrNull()?.mapToUsecase()

            override suspend fun markAsDenied(id: InvitationId): Boolean =
                invitationsStorage.markAsDenied(id)
        }

        DenyInvitationUsecase(authRepository, storage)
    }
}
