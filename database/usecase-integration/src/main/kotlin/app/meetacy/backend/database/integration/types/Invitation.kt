package app.meetacy.backend.database.integration.types

import app.meetacy.backend.database.types.DatabaseInvitation
import app.meetacy.backend.usecase.types.FullInvitation

fun DatabaseInvitation.toUsecase() = FullInvitation(
    identity,
    expiryDate,
    invitedUserId,
    invitorUserId,
    meeting
)