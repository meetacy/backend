package app.meetacy.backend.infrastructure.factories.friends

import app.meetacy.backend.endpoint.friends.FriendsDependencies
import org.jetbrains.exposed.sql.Database

fun friendDependenciesFactory(
    db: Database
): FriendsDependencies = FriendsDependencies(
    friendsLocationDependencies = friendLocationDependenciesBuilder(db),
    addFriendRepository = addFriendRepository(db),
    listFriendsRepository = listFriendsRepository(db),
    deleteFriendRepository = deleteFriendRepositoryBuilder(db)
)
