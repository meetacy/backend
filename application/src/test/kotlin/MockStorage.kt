import app.meetacy.backend.endpoint.files.download.GetFileRepository
import app.meetacy.backend.endpoint.files.download.GetFileResult
import app.meetacy.backend.endpoint.files.upload.SaveFileRepository
import app.meetacy.backend.endpoint.files.upload.UploadFileResult
import app.meetacy.backend.endpoint.meetings.history.list.ListMeetingsHistoryRepository
import app.meetacy.backend.endpoint.meetings.history.list.ListMeetingsResult
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
import app.meetacy.backend.usecase.meetings.get.GetMeetingsViewsUsecase
import app.meetacy.backend.usecase.meetings.get.ViewMeetingsUsecase
import app.meetacy.backend.usecase.meetings.history.list.ListMeetingsHistoryUsecase
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
    DeleteFriendUsecase.Storage, ListMeetingsHistoryUsecase.Storage, GetMeetingsViewsRepository,
    CreateMeetingUsecase.Storage, CreateMeetingUsecase.ViewMeetingRepository,
    ParticipateMeetingUsecase.Storage, FilesRepository, AddMeetingAvatarUsecase.Storage,
    DeleteMeetingAvatarUsecase.Storage, DeleteMeetingUsecase.Storage, GetNotificationsUsecase.Storage,
    ReadNotificationsUsecase.Storage, SaveFileRepository, GetFileRepository, AddUserAvatarUsecase.Storage,
    DeleteUserAvatarUsecase.Storage, ViewMeetingsUsecase.Storage, ListMeetingsHistoryRepository,
    GetMeetingsViewsUsecase.ViewMeetingsRepository, GetMeetingsViewsUsecase.MeetingsProvider {

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
            userIdentities.map { userId ->
                users.firstOrNull {  user ->
                    user.identity.userId == userId
                }
            }.map { user ->
                if (user == null) return@map null
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

    private val meetings = mutableListOf<FullMeeting>()

    override suspend fun addMeeting(
        accessHash: AccessHash,
        creatorId: UserId,
        date: Date,
        location: Location,
        title: String?,
        description: String?
    ): FullMeeting = synchronized(this) {
        val meeting = FullMeeting(
            identity = MeetingIdentity(
                meetingId = MeetingId(meetings.size.toLong()),
                accessHash = accessHash
            ),
            creatorId, date, location, title, description
        )
        meetings += meeting
        return@synchronized meeting
    }

    private val viewMeetingUsecase = ViewMeetingsUsecase(
        getUsersViewsRepository = this,
        storage = this
    )

    override suspend fun getIsParticipates(
        viewerId: UserId,
        meetingIds: List<MeetingId>
    ): List<Boolean> = meetingIds.map { meetingId ->
        (viewerId to meetingId) in participants
    }

    override suspend fun getParticipantsCount(meetingIds: List<MeetingId>): List<Int> =
        meetingIds.map { meetingId ->
            participants.count { (_, currentMeetingId) -> meetingId == currentMeetingId }
        }

    override suspend fun viewMeeting(viewer: UserId, meeting: FullMeeting): MeetingView {
        return viewMeetingUsecase.viewMeetings(viewer, listOf(meeting)).first()
    }

    override suspend fun deleteMeeting(meetingId: MeetingId) {
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

    override suspend fun checkFile(identity: FileIdentity): Boolean {
        TODO("Not yet implemented")
    }

    private val getMeetingViewsUsecase = GetMeetingsViewsUsecase(
        viewMeetingsRepository = this,
        meetingsProvider = this
    )

    override suspend fun getMeetingsViewsOrNull(viewerId: UserId, meetingIds: List<MeetingId>): List<MeetingView?> {
        return getMeetingViewsUsecase.getMeetingsViewsOrNull(viewerId, meetingIds)
    }

    override suspend fun getMeetings(meetingIds: List<MeetingId>): List<FullMeeting?> =
        meetingIds.map { meetingId ->
            meetings.firstOrNull { meeting ->
                meeting.id == meetingId
            }
        }

    override suspend fun viewMeetings(viewerId: UserId, meetings: List<FullMeeting>): List<MeetingView> {
        return viewMeetingUsecase.viewMeetings(viewerId, meetings)
    }

    override suspend fun addAvatar(accessIdentity: AccessIdentity, avatarIdentity: FileIdentity) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAvatar(accessIdentity: AccessIdentity) {
        TODO("Not yet implemented")
    }

    override suspend fun getFriends(
        userId: UserId,
        amount: Amount,
        lastUserId: UserId?
    ): List<ListFriendsUsecase.FriendId> = synchronized(this) {
        friendRelations
            .reversed().asSequence()
            .filter { (paging, user, friend) ->
                user == userId && friendRelations.any { (_, userId, friendId) ->
                    userId == user && friendId == friend
                } && paging.long < (lastUserId?.long ?: Long.MAX_VALUE)
            }
            .take(amount.int)
            .map { (paging, _, friendId) ->
                ListFriendsUsecase.FriendId(paging, friendId)
            }
            .toList()
    }

    private val participants: MutableList<Pair<UserId, MeetingId>> = mutableListOf()

    override suspend fun getParticipatingMeetings(
        memberId: UserId,
        amount: Amount,
        lastMeetingId: MeetingId?
    ): List<MeetingId> = synchronized(this) {
        participants
            .reversed().asSequence()
            .filter { (userId, meetingId) ->
                (userId == memberId) &&
                        (meetingId.long < (lastMeetingId?.long ?: Long.MAX_VALUE))
            }
            .take(amount.int)
            .map { it.second }
            .toList()
    }

    override suspend fun addParticipant(
        participantId: UserId,
        meetingId: MeetingId
    ) = synchronized(this) {
        participants += participantId to meetingId
    }

    override suspend fun isParticipating(
        meetingId: MeetingId,
        userId: UserId
    ): Boolean = synchronized(this) {
        (userId to meetingId) in participants
    }

    override suspend fun getList(
        accessIdentity: AccessIdentity,
        amount: Amount,
        pagingId: PagingId?
    ): ListMeetingsResult {
        TODO("Not yet implemented")
    }
}
