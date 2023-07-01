package app.meetacy.backend.infrastructure.factories.friends

import app.meetacy.backend.database.integration.location.stream.DatabaseFriendsLocationStreamingStorage
import app.meetacy.backend.endpoint.friends.FriendsDependencies
import app.meetacy.backend.endpoint.friends.location.FriendsLocationDependencies
import app.meetacy.backend.infrastructure.factories.auth.authRepository
import app.meetacy.backend.infrastructure.factories.users.getUserViewsRepository
import app.meetacy.backend.usecase.integration.friends.location.stream.UsecaseStreamLocationRepository
import app.meetacy.backend.usecase.location.stream.FriendsLocationStreamingUsecase
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.GetUsersViewsRepository
import org.jetbrains.exposed.sql.Database

fun friendDependenciesFactory(
    db: Database,
    authRepository: AuthRepository = authRepository(db),
    getUsersViewsRepository: GetUsersViewsRepository = getUserViewsRepository(db)
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
    addFriendRepository = addFriendRepository(db),
    listFriendsRepository = listFriendsRepository(db),
    deleteFriendRepository = deleteFriendRepositoryBuilder(db)
)
