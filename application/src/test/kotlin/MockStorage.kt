import app.meetacy.backend.endpoint.files.download.GetFileRepository
import app.meetacy.backend.endpoint.files.download.GetFileResult
import app.meetacy.backend.endpoint.files.upload.SaveFileRepository
import app.meetacy.backend.endpoint.files.upload.UploadFileResult
import app.meetacy.backend.hash.integration.DefaultHashGenerator
import app.meetacy.backend.types.*
import app.meetacy.backend.usecase.auth.GenerateTokenUsecase
import app.meetacy.backend.usecase.email.ConfirmEmailUsecase
import app.meetacy.backend.usecase.email.LinkEmailUsecase
import app.meetacy.backend.usecase.friends.add.AddFriendUsecase
import app.meetacy.backend.usecase.friends.delete.DeleteFriendUsecase
import app.meetacy.backend.usecase.friends.list.ListFriendsUsecase
import app.meetacy.backend.usecase.meetings.avatar.add.AddMeetingAvatarUsecase
import app.meetacy.backend.usecase.meetings.avatar.delete.DeleteMeetingAvatarUsecase
import app.meetacy.backend.usecase.meetings.create.CreateMeetingUsecase
import app.meetacy.backend.usecase.meetings.delete.DeleteMeetingUsecase
import app.meetacy.backend.usecase.meetings.list.GetMeetingsListUsecase
import app.meetacy.backend.usecase.meetings.participate.ParticipateMeetingUsecase
import app.meetacy.backend.usecase.notification.GetNotificationsUsecase
import app.meetacy.backend.usecase.notification.ReadNotificationsUsecase
import app.meetacy.backend.usecase.types.*
import app.meetacy.backend.usecase.users.ViewUserUsecase
import app.meetacy.backend.usecase.users.avatar.add.AddUserAvatarUsecase
import app.meetacy.backend.usecase.users.avatar.delete.DeleteUserAvatarUsecase
import app.meetacy.backend.usecase.users.get.GetUsersViewsUsecase
import java.io.InputStream

object MockStorage : GenerateTokenUsecase.Storage, LinkEmailUsecase.Storage, AuthRepository,
    ConfirmEmailUsecase.Storage, GetUsersViewsRepository, GetUsersViewsUsecase.Storage,
    GetUsersViewsUsecase.ViewUserRepository, AddFriendUsecase.Storage, ListFriendsUsecase.Storage,
    DeleteFriendUsecase.Storage, GetMeetingsListUsecase.Storage, GetMeetingsViewsRepository,
    CreateMeetingUsecase.Storage, CreateMeetingUsecase.ViewMeetingRepository,
    ParticipateMeetingUsecase.Storage, FilesRepository, AddMeetingAvatarUsecase.Storage,
    DeleteMeetingAvatarUsecase.Storage, DeleteMeetingUsecase.Storage, GetNotificationsUsecase.Storage,
    ReadNotificationsUsecase.Storage, SaveFileRepository, GetFileRepository, AddUserAvatarUsecase.Storage,
    DeleteUserAvatarUsecase.Storage {

    private val users = mutableListOf<User>()

    override suspend fun createUser(nickname: String): UserId {
        synchronized(lock = this) {
            val userId = UserId(users.size.toLong())
            val accessHash = AccessHash(DefaultHashGenerator.generate())
            val user = User(UserIdentity(userId, accessHash), nickname)
            users += user
            return user.identity.userId
        }
    }

    override suspend fun addToken(accessIdentity: AccessIdentity) {
        synchronized(lock = this) {
            users.replaceAll { user ->
                if (user.identity.userId != accessIdentity.userId) return@replaceAll user
                user.copy(tokens = user.tokens + accessIdentity)
            }
        }
    }

    private data class User(
        val identity: UserIdentity,
        val nickname: String,
        val email: String? = null,
        val emailVerified: Boolean = false,
        val tokens: List<AccessIdentity> = emptyList(),
        val avatarIdentity: FileIdentity? = null
    )

    override suspend fun isEmailOccupied(email: String): Boolean =
        synchronized(lock = this) {
            users.any { user -> user.email == email }
        }

    override suspend fun updateEmail(userId: UserId, email: String) {
        synchronized(lock = this) {
            users.replaceAll { user ->
                if (user.identity.userId != userId) return@replaceAll user
                user.copy(email = email)
            }
        }
    }

    private val confirmHashes = mutableListOf<Triple<UserId, String, String>>()

    override suspend fun addConfirmationHash(userId: UserId, email: String, confirmationHash: String) {
        synchronized(lock = this) {
            confirmHashes += Triple(userId, email, confirmationHash)
        }
    }

    override suspend fun authorize(accessIdentity: AccessIdentity): Boolean =
        synchronized(lock = this) {
            users.any { user ->
                user.identity.userId == accessIdentity.userId && user.tokens.any { token -> token == accessIdentity }
            }
        }

    override suspend fun getConfirmHashOwnerId(email: String, confirmHash: String): UserId? =
        synchronized(lock = this) {
            confirmHashes.firstOrNull { (_, currentEmail, currentConfirmHash) ->
                currentEmail == email && currentConfirmHash == confirmHash
            }?.first
        }

    override suspend fun deleteHashes(email: String) {
        synchronized(lock = this) {
            confirmHashes.removeIf { (_, currentEmail) -> currentEmail == email }
        }
    }

    override suspend fun verifyEmail(userIdentity: UserId) = synchronized(lock = this) {
        users.replaceAll { user ->
            if (user.identity.userId != userIdentity) return@replaceAll user
            user.copy(emailVerified = true)
        }
    }

    private val getUsersViewsUsecase = GetUsersViewsUsecase(
        storage = this,
        viewUserRepository = this
    )

    override suspend fun getUsersViewsOrNull(
        viewerId: UserId,
        userIdentities: List<UserId>
    ): List<UserView?> = getUsersViewsUsecase.viewUsers(viewerId, userIdentities)

    override suspend fun getUsers(userIdentities: List<UserId>): List<FullUser?> =
        synchronized(lock = this) {
            users.map { user ->
                with(user) {
                    FullUser(identity, nickname, email, emailVerified, avatarIdentity)
                }
            }
        }

    private val viewUserUsecase = ViewUserUsecase()

    override suspend fun viewUser(viewerId: UserId, user: FullUser): UserView {
        return viewUserUsecase.viewUser(viewerId, user)
    }

    private val friendRelations = mutableListOf<Triple<PagingId, UserId, UserId>>()

    override suspend fun addFriend(userId: UserId, friendId: UserId) {
        synchronized(lock = this) {
            friendRelations += Triple(PagingId(friendRelations.size.toLong()), userId, friendId)
        }
    }

    override suspend fun deleteFriend(userId: UserId, friendId: UserId) {
        synchronized(lock = this) {
            friendRelations.removeIf { (_, user, friend) -> user == userId && friend == friendId }
        }
    }

    override suspend fun isSubscribed(userId: UserId, friendId: UserId): Boolean = synchronized(lock = this) {
        friendRelations.any { (_, user, friend) -> userId == user && friendId == friend }
    }

    override suspend fun getFriends(
        userId: UserId,
        amount: Amount,
        pagingId: PagingId?
    ): List<ListFriendsUsecase.FriendId> = synchronized(lock = this) {
        friendRelations
            .reversed().asSequence()
            .filter { (paging, user, friend) ->
                user == userId && friendRelations.any { (_, userId, friendId) ->
                    userId == user && friendId == friend
                } && paging.long < (pagingId?.long ?: Long.MAX_VALUE)
            }
            .take(amount.int)
            .map { (paging, _, friendId) ->
                ListFriendsUsecase.FriendId(paging, friendId)
            }
            .toList()
    }

    override suspend fun getFile(fileIdentity: FileIdentity): GetFileResult {
        TODO("Not yet implemented")
    }

    override suspend fun saveFile(
        accessIdentity: AccessIdentity,
        fileName: String,
        inputProvider: () -> InputStream
    ): UploadFileResult {
        TODO("Not yet implemented")
    }

    override suspend fun addAvatar(meetingIdentity: MeetingIdentity, avatarIdentity: FileIdentity) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAvatar(meetingId: MeetingId) {
        TODO("Not yet implemented")
    }

    override suspend fun addMeeting(
        accessHash: AccessHash,
        creatorId: UserId,
        date: Date,
        location: Location,
        title: String?,
        description: String?
    ): FullMeeting {
        TODO("Not yet implemented")
    }

    override suspend fun viewMeeting(viewer: UserId, meeting: FullMeeting): MeetingView {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMeeting(meetingId: MeetingId) {
        TODO("Not yet implemented")
    }

    override suspend fun getSelfMeetings(creatorId: UserId): List<MeetingId> {
        TODO("Not yet implemented")
    }

    override suspend fun getParticipatingMeetings(memberId: UserId): List<MeetingId> {
        TODO("Not yet implemented")
    }

    override suspend fun addParticipant(meetingId: MeetingId, userId: UserId) {
        TODO("Not yet implemented")
    }

    override suspend fun isParticipating(meetingId: MeetingId, userId: UserId): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getLastReadNotification(userId: UserId): NotificationId {
        TODO("Not yet implemented")
    }

    override suspend fun getNotifications(
        userId: UserId,
        offset: Long,
        count: Int
    ): List<GetNotificationsUsecase.NotificationFromStorage> {
        TODO("Not yet implemented")
    }

    override suspend fun markAsRead(userId: UserId, lastNotificationId: NotificationId) {
        TODO("Not yet implemented")
    }

    override suspend fun notificationExists(notificationId: NotificationId): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun authorize(fileIdentity: FileIdentity): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getMeetingsViewsOrNull(viewerId: UserId, meetingIds: List<MeetingId>): List<MeetingView?> {
        TODO("Not yet implemented")
    }

    override suspend fun addAvatar(accessIdentity: AccessIdentity, avatarIdentity: FileIdentity) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAvatar(accessIdentity: AccessIdentity) {
        TODO("Not yet implemented")
    }
}
