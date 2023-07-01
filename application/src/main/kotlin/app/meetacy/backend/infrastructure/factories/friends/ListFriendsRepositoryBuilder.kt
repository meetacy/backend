package app.meetacy.backend.infrastructure.factories.friends

import app.meetacy.backend.database.integration.friends.get.DatabaseGetFriendsStorage
import app.meetacy.backend.endpoint.friends.list.ListFriendsRepository
import app.meetacy.backend.infrastructure.factories.auth.authRepository
import app.meetacy.backend.infrastructure.factories.users.getUserViewsRepository
import app.meetacy.backend.usecase.friends.list.ListFriendsUsecase
import app.meetacy.backend.usecase.integration.friends.get.UsecaseListFriendsRepository
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.GetUsersViewsRepository
import org.jetbrains.exposed.sql.Database

fun listFriendsRepository(
    db: Database,
    authRepository: AuthRepository = authRepository(db),
    getUsersViewsRepository: GetUsersViewsRepository = getUserViewsRepository(db)
): ListFriendsRepository = UsecaseListFriendsRepository(
    usecase = ListFriendsUsecase(
        authRepository = authRepository,
        getUsersViewsRepository = getUsersViewsRepository,
        storage = DatabaseGetFriendsStorage(db)
    )
)
