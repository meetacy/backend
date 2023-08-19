package app.meetacy.backend.application.database.invitations.view

import app.meetacy.backend.feature.invitations.database.integration.invitations.get.DatabaseGetInvitationsViewsUsecaseInvitationsProvider
import app.meetacy.backend.feature.invitations.database.integration.types.UsecaseGetInvitationsViewsRepository
import app.meetacy.backend.feature.invitations.database.integration.types.UsecaseViewInvitationsRepository
import app.meetacy.backend.application.database.database
import app.meetacy.backend.infrastructure.database.meetings.get.getMeetingViewRepository
import app.meetacy.backend.infrastructure.database.users.get.getUserViewsRepository
import app.meetacy.backend.feature.invitations.usecase.get.GetInvitationsViewsUsecase
import app.meetacy.backend.feature.invitations.usecase.get.ViewInvitationsUsecase
import app.meetacy.backend.feature.invitations.usecase.types.GetInvitationsViewsRepository
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.getInvitationsViewsRepository: GetInvitationsViewsRepository by Dependency

fun DIBuilder.getInvitationsViewsRepository() {
    val getInvitationsViewsRepository by singleton<GetInvitationsViewsRepository> {
        UsecaseGetInvitationsViewsRepository(
            usecase = GetInvitationsViewsUsecase(
                viewInvitationsRepository = UsecaseViewInvitationsRepository(
                    usecase = ViewInvitationsUsecase(
                        usersRepository = getUserViewsRepository,
                        meetingsRepository = getMeetingViewRepository
                    )
                ),
                invitationsProvider = DatabaseGetInvitationsViewsUsecaseInvitationsProvider(database)
            )
        )
    }
}
