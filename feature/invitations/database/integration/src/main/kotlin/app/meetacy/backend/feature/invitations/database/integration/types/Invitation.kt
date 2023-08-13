package app.meetacy.backend.feature.invitations.database.integration.types

import app.meetacy.backend.feature.invitations.database.types.DatabaseInvitation
import app.meetacy.backend.feature.invitations.usecase.types.FullInvitation

fun DatabaseInvitation.mapToUsecase() = FullInvitation(
    id = id,
    invitedUserId = invitedUserId,
    inviterUserId = inviterUserId,
    meetingId = meetingId,
    isAccepted = isAccepted
)
