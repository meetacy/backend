package app.meetacy.backend.infrastructure.factories.friends

import app.meetacy.backend.database.integration.friends.add.DatabaseAddFriendStorage
import app.meetacy.backend.endpoint.friends.add.AddFriendRepository
import app.meetacy.backend.infrastructure.factories.auth.authRepository
import app.meetacy.backend.infrastructure.factories.notifications.addNotificationUsecase
import app.meetacy.backend.infrastructure.factories.users.getUserViewsRepository
import app.meetacy.backend.usecase.friends.add.AddFriendUsecase
import app.meetacy.backend.usecase.integration.friends.add.UsecaseAddFriendRepository
import app.meetacy.backend.usecase.invitations.add.AddNotificationUsecase
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.GetUsersViewsRepository
import org.jetbrains.exposed.sql.Database

fun addFriendRepository(
    db: Database,
    authRepository: AuthRepository = authRepository(db),
    getUsersViewsRepository: GetUsersViewsRepository = getUserViewsRepository(db),
    addNotificationUsecase: AddNotificationUsecase = addNotificationUsecase(db)
): AddFriendRepository = UsecaseAddFriendRepository(
    usecase = AddFriendUsecase(
        authRepository = authRepository,
        getUsersViewsRepository = getUsersViewsRepository,
        storage = DatabaseAddFriendStorage(db, addNotificationUsecase)
    )
)
