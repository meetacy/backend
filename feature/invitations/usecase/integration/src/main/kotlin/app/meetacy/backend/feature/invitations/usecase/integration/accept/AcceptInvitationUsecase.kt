package app.meetacy.backend.feature.invitations.usecase.integration.accept

import app.meetacy.backend.feature.invitations.database.invitations.InvitationsStorage
import app.meetacy.backend.feature.invitations.usecase.accept.AcceptInvitationUsecase
import app.meetacy.backend.feature.invitations.usecase.integration.types.mapToUsecase
import app.meetacy.backend.types.invitation.FullInvitation
import app.meetacy.backend.feature.meetings.database.meetings.MeetingsStorage
import app.meetacy.backend.feature.meetings.database.meetings.ParticipantsStorage
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.meetings.FullMeeting
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.users.UserId
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.acceptInvitation() {
    val acceptInvitationUsecase by singleton<AcceptInvitationUsecase> {
        val authRepository: AuthRepository by getting
        val storage = object : AcceptInvitationUsecase.Storage {
            private val invitationsStorage: InvitationsStorage by getting
            private val participantsStorage: ParticipantsStorage by getting
            private val meetingsStorage: MeetingsStorage by getting

            override suspend fun getMeetingOrNull(id: MeetingId): FullMeeting? =
                meetingsStorage.getMeetingOrNull(id)

            override suspend fun getInvitationOrNull(id: InvitationId): FullInvitation? =
                invitationsStorage.getInvitationsOrNull(listOf(id)).singleOrNull()?.mapToUsecase()

            override suspend fun isParticipating(meetingId: MeetingId, userId: UserId): Boolean =
                participantsStorage.isParticipating(meetingId, userId)

            override suspend fun markAsAccepted(id: InvitationId) {
                invitationsStorage.markAsAccepted(id)
            }

            override suspend fun addParticipant(meetingId: MeetingId, userId: UserId) {
                participantsStorage.addParticipant(userId, meetingId)
            }
        }

        AcceptInvitationUsecase(authRepository, storage)
    }
}
