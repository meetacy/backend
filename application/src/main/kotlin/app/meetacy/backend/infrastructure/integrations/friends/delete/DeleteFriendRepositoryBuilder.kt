package app.meetacy.backend.infrastructure.integrations.friends.delete

import app.meetacy.backend.database.integration.friends.delete.DatabaseDeleteFriendStorage
import app.meetacy.backend.endpoint.friends.delete.DeleteFriendRepository
import app.meetacy.backend.infrastructure.integrations.auth.authRepository
import app.meetacy.backend.infrastructure.integrations.users.get.getUserViewsRepository
import app.meetacy.backend.usecase.friends.delete.DeleteFriendUsecase
import app.meetacy.backend.usecase.integration.friends.delete.UsecaseDeleteFriendRepository
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.GetUsersViewsRepository
import org.jetbrains.exposed.sql.Database

fun deleteFriendRepositoryBuilder(
    db: Database,
    authRepository: AuthRepository = authRepository(db),
    getUsersViewsRepository: GetUsersViewsRepository = getUserViewsRepository(db)
): DeleteFriendRepository = UsecaseDeleteFriendRepository(
    usecase = DeleteFriendUsecase(
        authRepository = authRepository,
        getUsersViewsRepository = getUsersViewsRepository,
        storage = DatabaseDeleteFriendStorage(db)
    )
)
