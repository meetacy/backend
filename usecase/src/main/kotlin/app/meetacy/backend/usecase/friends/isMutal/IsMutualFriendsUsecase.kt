package app.meetacy.backend.usecase.friends.isMutal

//import app.meetacy.backend.types.access.AccessIdentity
//import app.meetacy.backend.types.user.UserId
//import app.meetacy.backend.usecase.friends.list.ListFriendsUsecase
//import app.meetacy.backend.usecase.types.AuthRepository
//import app.meetacy.backend.usecase.types.GetUsersViewsRepository
//import app.meetacy.backend.usecase.types.authorize
//
//class IsMutualFriendsUsecase(
//    private val authRepository: AuthRepository,
//    private val getUsersViewsRepository: GetUsersViewsRepository,
////    private val storage: ListFriendsUsecase.Storage,
//) {
//
//    suspend fun isMutualFriendsUsecase(
//        accessIdentity: AccessIdentity,
//        viewerId: UserId,
//        isFriendId: UserId
//    ): Result {
//        authRepository.authorize(accessIdentity, fallback = { return Result.InvalidAccessIdentity })
//
//        val viewerFriends = getUsersViewsRepository.getUsersViewsOrNull(viewerId, listOf()).forEach {
//            it?.isSelf
//        }
//
//    }
//
//    sealed interface Result {
//        object Success : Result
//        object InvalidAccessIdentity : Result
//    }
//
//    interface Storage {
//        suspend fun isSubscribed(viewerId: UserId, isFriendId: UserId): Boolean
//    }
//
//}