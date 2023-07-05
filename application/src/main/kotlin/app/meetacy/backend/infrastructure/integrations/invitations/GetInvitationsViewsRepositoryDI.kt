package app.meetacy.backend.infrastructure.integrations.invitations

import app.meetacy.backend.database.integration.invitations.get.DatabaseGetInvitationsViewsUsecaseInvitationsProvider
import app.meetacy.backend.database.integration.types.UsecaseGetInvitationsViewsRepository
import app.meetacy.backend.database.integration.types.UsecaseViewInvitationsRepository
import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.di.dependency.Dependency
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.integrations.meetings.get.getMeetingsViewsRepository
import app.meetacy.backend.infrastructure.integrations.users.get.getUserViewsRepository
import app.meetacy.backend.usecase.invitations.get.GetInvitationsViewsUsecase
import app.meetacy.backend.usecase.invitations.get.ViewInvitationsUsecase
import app.meetacy.backend.usecase.types.GetInvitationsViewsRepository

val DI.getInvitationsViewsRepository: GetInvitationsViewsRepository by Dependency

fun DIBuilder.getInvitationsViewsRepository() {
    val getInvitationsViewsRepository by singleton<GetInvitationsViewsRepository> {
        UsecaseGetInvitationsViewsRepository(
            usecase = GetInvitationsViewsUsecase(
                viewInvitationsRepository = UsecaseViewInvitationsRepository(
                    usecase = ViewInvitationsUsecase(
                        usersRepository = getUserViewsRepository,
                        meetingsRepository = getMeetingsViewsRepository(database)
                    )
                ),
                invitationsProvider = DatabaseGetInvitationsViewsUsecaseInvitationsProvider(database)
            )
        )
    }
}
