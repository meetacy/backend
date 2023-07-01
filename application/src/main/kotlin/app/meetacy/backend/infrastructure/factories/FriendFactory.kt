package app.meetacy.backend.infrastructure.factories

import app.meetacy.backend.database.integration.friends.add.DatabaseAddFriendStorage
import app.meetacy.backend.database.integration.friends.delete.DatabaseDeleteFriendStorage
import app.meetacy.backend.database.integration.friends.get.DatabaseGetFriendsStorage
import app.meetacy.backend.database.integration.location.stream.DatabaseFriendsLocationStreamingStorage
import app.meetacy.backend.endpoint.friends.FriendsDependencies
import app.meetacy.backend.endpoint.friends.location.FriendsLocationDependencies
import app.meetacy.backend.infrastructure.factories.auth.authRepository
import app.meetacy.backend.usecase.friends.add.AddFriendUsecase
import app.meetacy.backend.usecase.friends.delete.DeleteFriendUsecase
import app.meetacy.backend.usecase.friends.list.ListFriendsUsecase
import app.meetacy.backend.usecase.integration.friends.add.UsecaseAddFriendRepository
import app.meetacy.backend.usecase.integration.friends.delete.UsecaseDeleteFriendRepository
import app.meetacy.backend.usecase.integration.friends.get.UsecaseListFriendsRepository
import app.meetacy.backend.usecase.integration.friends.location.stream.UsecaseStreamLocationRepository
import app.meetacy.backend.usecase.invitations.add.AddNotificationUsecase
import app.meetacy.backend.usecase.location.stream.FriendsLocationStreamingUsecase
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.GetUsersViewsRepository
import org.jetbrains.exposed.sql.Database

fun friendDependenciesFactory(
    db: Database,
    addNotificationUsecase: AddNotificationUsecase,
    authRepository: AuthRepository = authRepository(db),
    getUsersViewsRepository: GetUsersViewsRepository
): FriendsDependencies = FriendsDependencies(
    friendsLocationDependencies = FriendsLocationDependencies(
        streamLocationRepository = UsecaseStreamLocationRepository(
            usecase = FriendsLocationStreamingUsecase(
                authRepository = authRepository,
                storage = DatabaseFriendsLocationStreamingStorage(db),
                usersViewsRepository = getUsersViewsRepository
            )
        )
    ),
    addFriendRepository = UsecaseAddFriendRepository(
        usecase = AddFriendUsecase(
            authRepository = authRepository,
            getUsersViewsRepository = getUsersViewsRepository,
            storage = DatabaseAddFriendStorage(db, addNotificationUsecase)
        )
    ),
    listFriendsRepository = UsecaseListFriendsRepository(
        usecase = ListFriendsUsecase(
            authRepository = authRepository,
            getUsersViewsRepository = getUsersViewsRepository,
            storage = DatabaseGetFriendsStorage(db)
        )
    ),
    deleteFriendRepository = UsecaseDeleteFriendRepository(
        usecase = DeleteFriendUsecase(
            authRepository = authRepository,
            getUsersViewsRepository = getUsersViewsRepository,
            storage = DatabaseDeleteFriendStorage(db)
        )
    )
)
