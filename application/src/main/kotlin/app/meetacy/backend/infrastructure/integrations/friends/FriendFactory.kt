package app.meetacy.backend.infrastructure.integrations.friends

import app.meetacy.backend.endpoint.friends.FriendsDependencies
import app.meetacy.backend.infrastructure.integrations.friends.add.addFriendRepository
import app.meetacy.backend.infrastructure.integrations.friends.delete.deleteFriendRepositoryBuilder
import app.meetacy.backend.infrastructure.integrations.friends.list.listFriendsRepository
import app.meetacy.backend.infrastructure.integrations.friends.location.stream.friendLocationDependenciesBuilder
import org.jetbrains.exposed.sql.Database

fun friendDependenciesFactory(
    db: Database
): FriendsDependencies = FriendsDependencies(
    friendsLocationDependencies = friendLocationDependenciesBuilder(db),
    addFriendRepository = addFriendRepository(db),
    listFriendsRepository = listFriendsRepository(db),
    deleteFriendRepository = deleteFriendRepositoryBuilder(db)
)
