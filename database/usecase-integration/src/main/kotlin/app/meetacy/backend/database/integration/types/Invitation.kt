package app.meetacy.backend.database.integration.types

import app.meetacy.backend.database.types.DatabaseInvitation
import app.meetacy.backend.usecase.types.Invitation

fun DatabaseInvitation.toUsecase() = Invitation(
    identity,
    expiryDate,
    invitedUserId,
    invitorUserId,
    meeting,
    title,
    description
)