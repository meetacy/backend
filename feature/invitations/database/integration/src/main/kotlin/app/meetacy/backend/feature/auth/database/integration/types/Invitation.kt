package app.meetacy.backend.feature.auth.database.integration.types

import app.meetacy.backend.database.types.DatabaseInvitation
import app.meetacy.backend.feature.auth.usecase.types.FullInvitation

fun DatabaseInvitation.mapToUsecase() = FullInvitation(
    id = id,
    invitedUserId = invitedUserId,
    inviterUserId = inviterUserId,
    meetingId = meetingId,
    isAccepted = isAccepted
)
