
import app.meetacy.backend.feature.files.endpoints.download.GetFileRepository
import app.meetacy.backend.feature.files.endpoints.download.GetFileResult
import app.meetacy.backend.feature.meetings.endpoints.history.list.ListMeetingsHistoryRepository
import app.meetacy.backend.feature.meetings.endpoints.history.list.ListMeetingsResult
import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.file.FileId
import app.meetacy.backend.types.file.FileIdentity
import app.meetacy.backend.types.file.FileSize
import app.meetacy.backend.types.integration.generator.BasicHashGenerator
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.location.LocationSnapshot
import app.meetacy.backend.types.meetings.*
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.optional.Optional
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.paging.PagingValue
import app.meetacy.backend.types.paging.pagingResultLong
import app.meetacy.backend.types.serializable.file.serializable
import app.meetacy.backend.types.serializable.file.type
import app.meetacy.backend.types.update.UpdateId
import app.meetacy.backend.types.users.*
import app.meetacy.backend.feature.auth.usecase.GenerateTokenUsecase
import app.meetacy.backend.feature.email.usecase.ConfirmEmailUsecase
import app.meetacy.backend.feature.email.usecase.LinkEmailUsecase
import app.meetacy.backend.feature.files.usecase.files.UploadFileUsecase
import app.meetacy.backend.feature.files.usecase.types.FilesRepository
import app.meetacy.backend.feature.friends.usecase.friends.add.AddFriendUsecase
import app.meetacy.backend.feature.friends.usecase.friends.delete.DeleteFriendUsecase
import app.meetacy.backend.feature.friends.usecase.friends.list.ListFriendsUsecase
import app.meetacy.backend.feature.invitations.usecase.invitations.accept.AcceptInvitationUsecase
import app.meetacy.backend.feature.invitations.usecase.invitations.cancel.CancelInvitationUsecase
import app.meetacy.backend.feature.invitations.usecase.invitations.create.CreateInvitationUsecase
import app.meetacy.backend.feature.invitations.usecase.invitations.deny.DenyInvitationUsecase
import app.meetacy.backend.feature.invitations.usecase.invitations.get.GetInvitationsViewsUsecase
import app.meetacy.backend.feature.invitations.usecase.invitations.get.ViewInvitationsUsecase
import app.meetacy.backend.feature.friends.usecase.location.stream.FriendsLocationStreamingUsecase
import app.meetacy.backend.feature.friends.usecase.location.stream.LocationsMiddleware
import app.meetacy.backend.feature.invitations.usecase.types.FullInvitation
import app.meetacy.backend.feature.invitations.usecase.types.GetInvitationsViewsRepository
import app.meetacy.backend.feature.invitations.usecase.types.InvitationView
import app.meetacy.backend.feature.invitations.usecase.types.ViewInvitationsRepository
import app.meetacy.backend.feature.meetings.usecase.create.CreateMeetingUsecase
import app.meetacy.backend.feature.meetings.usecase.delete.DeleteMeetingUsecase
import app.meetacy.backend.feature.meetings.usecase.edit.EditMeetingUsecase
import app.meetacy.backend.feature.meetings.usecase.get.GetMeetingsViewsUsecase
import app.meetacy.backend.feature.meetings.usecase.get.ViewMeetingsUsecase
import app.meetacy.backend.feature.meetings.usecase.history.active.ListMeetingsActiveUsecase
import app.meetacy.backend.feature.meetings.usecase.history.list.ListMeetingsHistoryUsecase
import app.meetacy.backend.feature.meetings.usecase.history.past.ListMeetingsPastUsecase
import app.meetacy.backend.feature.meetings.usecase.map.list.ListMeetingsMapUsecase
import app.meetacy.backend.feature.meetings.usecase.participants.list.ListMeetingParticipantsUsecase
import app.meetacy.backend.feature.meetings.usecase.participate.ParticipateMeetingUsecase
import app.meetacy.backend.feature.notifications.usecase.notifications.ReadNotificationsUsecase
import app.meetacy.backend.feature.notifications.usecase.notifications.add.AddNotificationUsecase
import app.meetacy.backend.feature.notifications.usecase.notifications.get.GetNotificationsUsecase
import app.meetacy.backend.feature.notifications.usecase.notifications.get.GetNotificationsViewsUsecase
import app.meetacy.backend.feature.notifications.usecase.notifications.get.ViewNotificationsUsecase
import app.meetacy.backend.feature.notifications.usecase.types.FullNotification
import app.meetacy.backend.feature.notifications.usecase.types.GetNotificationsViewsRepository
import app.meetacy.backend.feature.notifications.usecase.types.NotificationView
import app.meetacy.backend.feature.notifications.usecase.types.ViewNotificationsRepository
import app.meetacy.backend.feature.updates.usecase.types.FullUpdate
import app.meetacy.backend.feature.updates.usecase.updates.stream.StreamUpdatesUsecase
import app.meetacy.backend.feature.updates.usecase.updates.stream.UpdatesMiddleware
import app.meetacy.backend.feature.users.usecase.edit.EditUserUsecase
import app.meetacy.backend.feature.users.usecase.get.GetUsersViewsUsecase
import app.meetacy.backend.feature.users.usecase.get.ViewUserUsecase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import java.io.File
import app.meetacy.backend.types.serializable.file.FileIdentity as FileIdentitySerializable

class MockStorage : GenerateTokenUsecase.Storage, LinkEmailUsecase.Storage, AuthRepository,
    ConfirmEmailUsecase.Storage, GetUsersViewsRepository, GetUsersViewsUsecase.Storage,
    GetUsersViewsUsecase.ViewUserRepository, AddFriendUsecase.Storage, ListFriendsUsecase.Storage,
    DeleteFriendUsecase.Storage, ListMeetingsHistoryUsecase.Storage, GetMeetingsViewsRepository,
    CreateMeetingUsecase.Storage, CreateMeetingUsecase.ViewMeetingRepository,
    ParticipateMeetingUsecase.Storage, FilesRepository, DeleteMeetingUsecase.Storage, GetNotificationsUsecase.Storage,
    ReadNotificationsUsecase.Storage, GetFileRepository, ViewMeetingsUsecase.Storage, ListMeetingsHistoryRepository,
    ViewMeetingsRepository, GetMeetingsViewsUsecase.MeetingsProvider,
    ListMeetingsMapUsecase.Storage, EditMeetingUsecase.Storage, EditUserUsecase.Storage,
    ListMeetingParticipantsUsecase.Storage, CheckMeetingRepository, UploadFileUsecase.Storage, FriendsLocationStreamingUsecase.Storage,
    CreateInvitationUsecase.Storage, AcceptInvitationUsecase.Storage, DenyInvitationUsecase.Storage,
    CancelInvitationUsecase.Storage, ViewUserUsecase.Storage, GetInvitationsViewsRepository,
    ListMeetingsActiveUsecase.Storage, ListMeetingsPastUsecase.Storage, ValidateRepository,
    ViewNotificationsRepository, ViewNotificationsUsecase.Storage, StreamUpdatesUsecase.Storage,
    GetNotificationsViewsRepository, GetNotificationsViewsUsecase.Storage, UpdatesMiddleware.Storage,
    ViewInvitationsRepository, GetInvitationsViewsUsecase.InvitationsProvider, AddNotificationUsecase.Storage {

    private val users = mutableListOf<User>()

    override suspend fun createUser(nickname: String): UserId {
        synchronized(lock = this) {
            val userId = UserId(users.size.toLong())
            val accessHash = AccessHash(BasicHashGenerator.generate())
            val user = User(UserIdentity(userId, accessHash), nickname)
            users += user
            return user.identity.id
        }
    }

    override suspend fun addToken(accessIdentity: AccessIdentity) {
        synchronized(lock = this) {
            users.replaceAll { user ->
                if (user.identity.id != accessIdentity.userId) return@replaceAll user
                user.copy(tokens = user.tokens + accessIdentity)
            }
        }
    }

    private data class User(
        val identity: UserIdentity,
        val nickname: String,
        val username: Username? = null,
        val email: String? = null,
        val emailVerified: Boolean = false,
        val tokens: List<AccessIdentity> = emptyList(),
        val avatarId: FileId? = null
    )

    override suspend fun isEmailOccupied(email: String): Boolean =
        synchronized(lock = this) {
            users.any { user -> user.email == email }
        }

    override suspend fun updateEmail(userId: UserId, email: String) {
        synchronized(lock = this) {
            users.replaceAll { user ->
                if (user.identity.id != userId) return@replaceAll user
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
                user.identity.id == accessIdentity.userId && user.tokens.any { token -> token == accessIdentity }
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
            if (user.identity.id != userIdentity) return@replaceAll user
            user.copy(emailVerified = true)
        }
    }

    private val getUsersViewsUsecase = GetUsersViewsUsecase(
        storage = this,
        viewUserRepository = this
    )
    private val viewUserUsecase = ViewUserUsecase(
        filesRepository = this,
        storage = this
    )

    override suspend fun getUsersViewsOrNull(
        viewerId: UserId,
        userIdentities: List<UserId>
    ): List<UserView?> = getUsersViewsUsecase.viewUsers(viewerId, userIdentities)

    override suspend fun getUsers(userIdentities: List<UserId>): List<FullUser?> =
        synchronized(lock = this) {
            userIdentities.map { userId ->
                users.firstOrNull {  user ->
                    user.identity.id == userId
                }
            }.map { user ->
                if (user == null) return@map null
                with(user) {
                    FullUser(identity, nickname, username, email, emailVerified, avatarId)
                }
            }
        }

    private val friendRelations = mutableListOf<Triple<PagingId, UserId, UserId>>()

    override suspend fun addFriend(userId: UserId, friendId: UserId) {
        synchronized(lock = this) {
            friendRelations += Triple(PagingId(friendRelations.size.toLong()), userId, friendId)
        }
    }

    override suspend fun addInvitation(
        userId: UserId,
        inviterId: UserId,
        meetingId: MeetingId,
        date: DateTime
    ): NotificationId {
        val id = NotificationId(notifications.size.toLong())
        notifications.add(
            userId to FullNotification.Invitation(id, date, meetingId, inviterId)
        )
        return id
    }

    override suspend fun addSubscription(
        userId: UserId,
        subscriberId: UserId,
        date: DateTime
    ): NotificationId {
        val id = NotificationId(notifications.size.toLong())
        notifications.add(
            userId to FullNotification.Subscription(id, date, subscriberId)
        )
        return id
    }

    override suspend fun addUpdate(userId: UserId, notificationId: NotificationId) {
        updatesMiddleware.addNotificationUpdate(userId, notificationId)
    }

    private val addNotificationUsecase = AddNotificationUsecase(storage = this)

    override suspend fun addNotification(userId: UserId, subscriberId: UserId) {
        addNotificationUsecase.addSubscription(userId, subscriberId, date = DateTime.now())
    }

    override suspend fun deleteFriend(userId: UserId, friendId: UserId) {
        synchronized(lock = this) {
            friendRelations.removeIf { (_, user, friend) -> user == userId && friend == friendId }
        }
    }

    override suspend fun isSubscribed(userId: UserId, friendId: UserId): Boolean = synchronized(lock = this) {
        friendRelations.any { (_, user, friend) -> userId == user && friendId == friend }
    }

    private val baseDir = File(
        /* parent = */ System.getenv("user.dir"),
        /* child = */ "files"
    ).apply { mkdirs() }.absolutePath

    override suspend fun getFile(fileId: FileIdentitySerializable): GetFileResult {
        val fileIdType = fileId.type()

        val file = files.firstOrNull { it.identity == fileIdType }
            ?: return GetFileResult.InvalidFileIdentity

        return GetFileResult.Success(
            file = File(baseDir, "${fileIdType.id.long}"),
            fileName = file.fileName,
            fileSize = file.size?.serializable() ?: return GetFileResult.InvalidFileIdentity
        )
    }

    private val meetings = mutableListOf<FullMeeting>()

    override suspend fun addMeeting(
        accessHash: AccessHash,
        creatorId: UserId,
        date: Date,
        location: Location,
        title: String?,
        description: String?,
        visibility: FullMeeting.Visibility,
        avatarId: FileId?
    ): FullMeeting = synchronized(this) {
        val meeting = FullMeeting(
            identity = MeetingIdentity(
                meetingId = MeetingId(meetings.size.toLong()),
                accessHash = accessHash
            ),
            creatorId, date, location, title, description, avatarId = null, visibility
        )
        meetings += meeting
        return@synchronized meeting
    }

    private val viewMeetingUsecase = ViewMeetingsUsecase(
        getUsersViewsRepository = this,
        storage = this,
        filesRepository = this
    )

    override suspend fun getIsParticipates(
        viewerId: UserId,
        meetingIds: List<MeetingId>
    ): List<Boolean> = meetingIds.map { meetingId ->
        isParticipating(meetingId, viewerId)
    }

    override suspend fun getFirstParticipants(
        limit: Amount,
        meetingIds: List<MeetingId>
    ): List<List<UserId>> = meetingIds.map { currentMeetingId ->
        participants.reversed()
            .filter { (_, _, meetingId) ->
                meetingId == currentMeetingId
            }
            .map { (_, userId, _) -> userId }
            .take(limit.int)
    }

    override suspend fun getParticipantsCount(meetingIds: List<MeetingId>): List<Int> =
        meetingIds.map { meetingId ->
            participants.count { (_, _, currentMeetingId) -> meetingId == currentMeetingId }
        }

    override suspend fun viewMeeting(viewer: UserId, meeting: FullMeeting): MeetingView {
        return viewMeetingUsecase.viewMeetings(viewer, listOf(meeting)).first()
    }

    override suspend fun deleteMeeting(meetingId: MeetingId) {
        TODO("Not yet implemented")
    }

    private val lastReadNotificationId: MutableMap<UserId, NotificationId> = mutableMapOf()

    override suspend fun getLastReadNotification(userId: UserId): NotificationId {
        return synchronized(this) {
            lastReadNotificationId[userId] ?: NotificationId(long = 0)
        }
    }

    override suspend fun markAsRead(userId: UserId, lastNotificationId: NotificationId) {
        synchronized(this) {
            lastReadNotificationId[userId] = lastNotificationId
        }
    }

    private val notifications: MutableList<Pair<UserId, FullNotification>> = mutableListOf()

    override suspend fun getNotifications(
        userId: UserId,
        pagingId: PagingId?,
        amount: Amount
    ): PagingResult<FullNotification> {
        return notifications.filter { (currentUserId, notification) ->
            userId == currentUserId && notification.id.long < (pagingId?.long ?: Long.MAX_VALUE)
        }.take(amount.int)
            .map { it.second }
            .pagingResultLong(amount) { it.id.long }
    }

    private val notificationsUsecase = GetNotificationsViewsUsecase(
        storage = this,
        viewRepository = this
    )

    override suspend fun getNotificationsViewsOrNull(
        viewerId: UserId,
        notificationIds: List<NotificationId>
    ): List<NotificationView?> {
        return notificationsUsecase.getNotificationsViewsOrNull(viewerId, notificationIds)
    }

    override suspend fun getNotifications(notificationIds: List<NotificationId>): List<FullNotification?> {
        return notificationIds.map { id -> notifications.firstOrNull { it.second.id == id }?.second }
    }

    private val viewNotificationsUsecase = ViewNotificationsUsecase(
        storage = this,
        meetingsRepository = this,
        usersRepository = this
    )

    override suspend fun viewNotifications(
        viewerId: UserId,
        notifications: List<FullNotification>
    ): List<NotificationView> {
        return viewNotificationsUsecase.viewNotifications(viewerId, notifications)
    }

    override suspend fun getLastReadNotificationId(userId: UserId): NotificationId {
        return getLastReadNotification(userId)
    }

    override suspend fun notificationExists(notificationId: NotificationId): Boolean {
        return notifications.any { (_, notification)  -> notification.id == notificationId }
    }

    override fun locationFlow(userId: UserId): Flow<LocationSnapshot> {
        return locationsMiddleware.locationFlow(userId)
    }

    override suspend fun getFileIdentities(fileIdList: List<FileId>): List<FileIdentity?> =
        files.filter { file ->
            file.identity.id in fileIdList
        }.map { it.identity }

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

    override suspend fun viewMeetings(
        viewerId: UserId,
        meetings: List<FullMeeting>
    ): List<MeetingView> {
        return viewMeetingUsecase.viewMeetings(viewerId, meetings)
    }

    override suspend fun getFriends(
        userId: UserId,
        amount: Amount,
        pagingId: PagingId?
    ): PagingResult<UserId> = synchronized(this) {
        val result = friendRelations
            .reversed().asSequence()
            .filter { (paging, user, friend) ->
                user == userId && friendRelations.any { (_, userId, friendId) ->
                    userId == user && friendId == friend
                } && paging.long < (pagingId?.long ?: Long.MAX_VALUE)
            }
            .take(amount.int)
            .map { (pagingId, _, friendId) -> pagingId to friendId }
            .toList()

        val nextPagingId = if (result.size == amount.int) result.last().first else null

        PagingResult(
            data = result.map { (_, userId) -> userId },
            nextPagingId = nextPagingId
        )
    }

    private val participants: MutableList<Triple<PagingId, UserId, MeetingId>> = mutableListOf()

    override suspend fun getParticipatingMeetings(
        memberId: UserId,
        amount: Amount,
        pagingId: PagingId?
    ): PagingResult<MeetingId> = synchronized(this) {
        val result = participants
            .reversed().asSequence()
            .filter { (id, userId) ->
                (userId == memberId) && (id.long < (pagingId?.long ?: Long.MAX_VALUE))
            }
            .take(amount.int)
            .map { (pagingId, _, meetingId) -> pagingId to meetingId }
            .toList()

        val nextPagingId = if (result.size == amount.int) result.last().first else null

        PagingResult(
            data = result.map { (_, meetingId) -> meetingId },
            nextPagingId = nextPagingId
        )
    }

    override suspend fun addParticipant(
        participantId: UserId,
        meetingId: MeetingId
    ) = synchronized(this) {
        participants += Triple(PagingId(participants.size.toLong()), participantId, meetingId)
    }

    override suspend fun getMeetingOrNull(id: MeetingId): FullMeeting? =
        getMeetings(listOf(id)).singleOrNull()

    override suspend fun getInvitationOrNull(id: InvitationId): FullInvitation? =
        invitations.firstOrNull { it.id == id }

    override suspend fun isParticipating(
        meetingId: MeetingId,
        userId: UserId
    ): Boolean = synchronized(this) {
        participants.any { (_, participantUserId, participantMeetingId) ->
            participantUserId == userId && participantMeetingId == meetingId
        }
    }

    override suspend fun markAsAccepted(id: InvitationId) {
        val invitation = invitations[invitations.indexOfFirst { it.id == id }]
        invitations[invitations.indexOfFirst { it.id == id }] = FullInvitation(
            invitation.id,
            invitation.invitedUserId,
            invitation.inviterUserId,
            invitation.meetingId,
            isAccepted = true
        )
    }

    override suspend fun addParticipant(meetingId: MeetingId, userId: UserId) =
        addParticipant(userId, meetingId)

    override suspend fun getList(
        accessIdentity: app.meetacy.backend.types.serializable.access.AccessIdentity,
        amount: app.meetacy.backend.types.serializable.amount.Amount,
        pagingId: app.meetacy.backend.types.paging.serializable.PagingId?
    ): ListMeetingsResult {
        TODO("Not yet implemented")
    }

    override suspend fun getMeetingsHistoryFlow(userId: UserId): Flow<MeetingId> =
        participants.asFlow()
            .filter { (_, memberId) -> memberId == userId }
            .map { (_, _, meetingId) -> meetingId }

    override suspend fun getPublicMeetingsFlow(): Flow<FullMeeting> = meetings
        .filter { it.visibility == FullMeeting.Visibility.Public }
        .asFlow()

    override suspend fun editMeeting(
        meetingId: MeetingId,
        avatarId: Optional<FileId?>,
        title: String?,
        description: String?,
        location: Location?,
        date: Date?,
        visibility: FullMeeting.Visibility?
    ): FullMeeting {
        synchronized(this) {
            meetings.replaceAll { meeting ->
                if (meeting.identity.id != meetingId) return@replaceAll meeting

                meeting.copy(
                    avatarId = if (avatarId is Optional.Present) avatarId.value else meeting.avatarId,
                    title = title ?: meeting.title,
                    description = description ?: meeting.description,
                    location = location ?: meeting.location,
                    date = date ?: meeting.date,
                    visibility = visibility ?: meeting.visibility
                )
            }
        }

        return getMeetings(listOf(meetingId)).first()!!
    }


    override suspend fun editUser(
        userId: UserId,
        nickname: Optional<String>,
        username: Optional<Username?>,
        avatarId: Optional<FileId?>
    ): FullUser {
        synchronized(this) {
            users.replaceAll { user ->
                if (user.identity.id != userId) return@replaceAll user

                user.copy(
                    nickname = nickname.value ?: user.nickname,
                    username = if (username is Optional.Present) username.value else user.username,
                    avatarId = if (avatarId is Optional.Present) avatarId.value else user.avatarId
                )
            }
        }
        return getUsers(listOf(userId)).first()!!
    }

    override suspend fun isOccupied(username: Username): Boolean = users.any { it.username == username }

    override suspend fun viewUser(viewerId: UserId, user: FullUser): UserView =
        viewUserUsecase.viewUser(viewerId, user)

    override suspend fun checkMeetingIdentity(identity: MeetingIdentity): Boolean =
        meetings.any { it.identity == identity }

    override suspend fun getMeetingParticipants(
        meetingId: MeetingId,
        amount: Amount,
        pagingId: PagingId?
    ): PagingResult<UserId> = synchronized(this) {
        val participants = participants
            .reversed()
            .filter { (_, _, id) -> id == meetingId }

        PagingResult(
            data = participants.map { it.second },
            nextPagingId = if (participants.size == amount.int) participants.last().first else null
        )
    }

    private val locationsMiddleware = LocationsMiddleware(LocationsMiddlewareStorage)

    override suspend fun setLocation(userId: UserId, location: Location) {
        locationsMiddleware.setLocation(userId, location)
    }

    override suspend fun getFriends(userId: UserId, maxAmount: Amount): List<UserId> {
        return getFriends(userId, maxAmount, pagingId = null).data
    }

    private val files = mutableListOf<File>()

    override suspend fun getUserWastedSize(userId: UserId): FileSize {
        val long = files.sumOf { file ->
            if (file.ownerId == userId) (file.size?.bytesSize ?: 0) else 0L
        }
        return FileSize(long)
    }

    override suspend fun saveFileDescription(
        userId: UserId,
        accessHash: AccessHash,
        fileName: String
    ): FileIdentity = synchronized(this) {
        val id = FileId(files.size.toLong())
        val identity = FileIdentity(id, accessHash)

        files += File(
            identity = identity,
            ownerId = userId,
            size = null,
            fileName = fileName
        )

        return FileIdentity(id, accessHash)
    }

    override suspend fun uploadFileSize(fileId: FileId, fileSize: FileSize) {
        for (file in files) {
            if (file.identity.id == fileId) {
                file.size = fileSize
            }
        }
    }

    private data class File(
        val identity: FileIdentity,
        val ownerId: UserId,
        var size: FileSize? = null,
        val fileName: String
    )

    private val invitations: MutableList<FullInvitation> = mutableListOf()

    override suspend fun isSubscriberOf(subscriberId: UserId, authorId: UserId): Boolean =
        isSubscribed(subscriberId, authorId)

    override suspend fun getMeeting(meetingId: MeetingId): FullMeeting? {
        return getMeetingOrNull(meetingId)
    }

    override suspend fun getUser(id: UserId): FullUser? {
        return getUsers(listOf(id)).singleOrNull()
    }

    override suspend fun getInvitationsFrom(authorId: UserId): List<FullInvitation> =
        invitations.filter { it.inviterUserId == authorId }

    override suspend fun createInvitation(
        accessHash: AccessHash,
        invitedUserId: UserId,
        inviterUserId: UserId,
        meetingId: MeetingId
    ): InvitationId {
        val lastId = InvitationId(invitations.size.toLong())

        invitations.add(
            FullInvitation(
                id = lastId,
                invitedUserId,
                inviterUserId,
                meetingId,
                isAccepted = null
            )
        )

        return lastId
    }

    override suspend fun addNotification(userId: UserId, inviterId: UserId, meetingId: MeetingId) {
        notifications.add(
            userId to FullNotification.Invitation(
                id = NotificationId(notifications.size.toLong()),
                date = DateTime.now(),
                meetingId = meetingId,
                inviterId = inviterId
            )
        )
    }

    override suspend fun cancel(id: InvitationId): Boolean {
        return invitations.removeAll { it.id == id }
    }

    override suspend fun getInvitation(id: InvitationId): FullInvitation? {
        return getInvitationOrNull(id)
    }

    override suspend fun markAsDenied(id: InvitationId): Boolean {
        val invitation = invitations[invitations.indexOfFirst { it.id == id }]
        invitations[invitations.indexOfFirst { it.id == id }] = FullInvitation(
            invitation.id,
            invitation.invitedUserId,
            invitation.inviterUserId,
            invitation.meetingId,
            isAccepted = false
        )
        return true
    }

    override suspend fun isSubscriber(userId: UserId, subscriberId: UserId): Boolean =
        getFriends(userId, Amount.parse(Int.MAX_VALUE)).contains(subscriberId)

    override suspend fun getJoinHistoryFlow(userId: UserId, startPagingId: PagingId?): Flow<PagingValue<MeetingId>> =
        participants.asFlow()
            .filter { (pagingId, memberId) -> memberId == userId
                    && pagingId.long < (startPagingId?.long ?: Long.MAX_VALUE) }
            .map { (pagingId, _, meetingId) -> PagingValue(
                    value = meetingId,
                    nextPagingId = pagingId
                )
            }

    private val updates: MutableList<Pair<UserId, FullUpdate>> = mutableListOf()

    override suspend fun addNotificationUpdate(userId: UserId, notificationId: NotificationId): UpdateId {
        val updateId = UpdateId(updates.size.toLong())
        updates.add(userId to FullUpdate.Notification(updateId, notificationId))
        return updateId
    }

    override fun pastUpdatesFlow(userId: UserId, fromId: UpdateId): Flow<FullUpdate> {
        return updates.asFlow()
            .filter { (currentUserId, update) -> currentUserId == userId && update.id.long > fromId.long }
            .map { (_, update) -> update }
    }

    private val updatesMiddleware = UpdatesMiddleware(storage = this)

    override suspend fun updatesFlow(userId: UserId, fromId: UpdateId?): Flow<FullUpdate> {
        return updatesMiddleware.updatesFlow(userId, fromId)
    }

    object LocationsMiddlewareStorage : LocationsMiddleware.Storage {
        private val locations = mutableMapOf<UserId, LocationSnapshot>()

        override suspend fun setLocation(userId: UserId, location: Location) = synchronized(locations) {
            locations[userId] = LocationSnapshot(location, DateTime.now())
        }

        override suspend fun getLocation(userId: UserId): LocationSnapshot? = synchronized(locations) {
            return locations[userId]
        }
    }

    private val getInvitationsViewsUsecase = GetInvitationsViewsUsecase(
        viewInvitationsRepository = this,
        invitationsProvider = this
    )

    override suspend fun getInvitationsViewsOrNull(
        viewerId: UserId,
        invitationIds: List<InvitationId>
    ): List<InvitationView?> {
        return getInvitationsViewsUsecase.getInvitationsViewsOrNull(viewerId, invitationIds)
    }

    private val viewInvitationsUsecase = ViewInvitationsUsecase(
        usersRepository = this,
        meetingsRepository = this
    )

    override suspend fun viewInvitations(
        viewerId: UserId,
        invitations: List<FullInvitation>
    ): List<InvitationView> {
        return viewInvitationsUsecase.viewInvitations(viewerId, invitations)
    }

    override suspend fun getInvitationsOrNull(
        invitationIds: List<InvitationId>
    ): List<FullInvitation?> {
        return invitationIds.map { id -> getInvitationOrNull(id) }
    }
}
