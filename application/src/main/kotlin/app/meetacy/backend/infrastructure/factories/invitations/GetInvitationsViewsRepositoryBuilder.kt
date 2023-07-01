package app.meetacy.backend.infrastructure.factories.invitations

import app.meetacy.backend.database.integration.types.GetInvitationsViewsRepository
import app.meetacy.backend.database.integration.types.ViewInvitationsRepository
import app.meetacy.backend.infrastructure.factories.meetings.get.getMeetingsViewsRepository
import app.meetacy.backend.infrastructure.factories.users.get.getUserViewsRepository
import app.meetacy.backend.usecase.types.GetInvitationsViewsRepository
import org.jetbrains.exposed.sql.Database

fun getInvitationsViewsRepository(db: Database): GetInvitationsViewsRepository = GetInvitationsViewsRepository(
    db = db,
    viewInvitationsRepository = ViewInvitationsRepository(
        usersRepository = getUserViewsRepository(db),
        meetingsRepository = getMeetingsViewsRepository(db)
    )
)
