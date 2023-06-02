
import app.meetacy.backend.database.types.DatabaseInvitation
import app.meetacy.backend.endpoint.files.download.GetFileRepository
import app.meetacy.backend.endpoint.files.download.GetFileResult
import app.meetacy.backend.endpoint.meetings.history.list.ListMeetingsHistoryRepository
import app.meetacy.backend.endpoint.meetings.history.list.ListMeetingsResult
import app.meetacy.backend.hash.integration.DefaultHashGenerator
import app.meetacy.backend.types.Optional
import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.file.FileId
import app.meetacy.backend.types.file.FileIdentity
import app.meetacy.backend.types.file.FileSize
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.location.LocationSnapshot
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.meeting.MeetingIdentity
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.types.user.UserIdentity
import app.meetacy.backend.usecase.auth.GenerateTokenUsecase
import app.meetacy.backend.usecase.email.ConfirmEmailUsecase
import app.meetacy.backend.usecase.email.LinkEmailUsecase
import app.meetacy.backend.usecase.files.UploadFileUsecase
import app.meetacy.backend.usecase.friends.add.AddFriendUsecase
import app.meetacy.backend.usecase.friends.delete.DeleteFriendUsecase
import app.meetacy.backend.usecase.friends.list.ListFriendsUsecase
import app.meetacy.backend.usecase.invitations.accept.AcceptInvitationUsecase
import app.meetacy.backend.usecase.invitations.cancel.CancelInvitationUsecase
import app.meetacy.backend.usecase.invitations.create.CreateInvitationUsecase
import app.meetacy.backend.usecase.invitations.deny.DenyInvitationUsecase
import app.meetacy.backend.usecase.invitations.read.ReadInvitationUsecase
import app.meetacy.backend.usecase.invitations.update.UpdateInvitationUsecase
import app.meetacy.backend.usecase.location.stream.BaseFriendsLocationStreamingStorage
import app.meetacy.backend.usecase.location.stream.LocationFlowStorage
import app.meetacy.backend.usecase.meetings.create.CreateMeetingUsecase
import app.meetacy.backend.usecase.meetings.delete.DeleteMeetingUsecase
import app.meetacy.backend.usecase.meetings.edit.EditMeetingUsecase
import app.meetacy.backend.usecase.meetings.get.GetMeetingsViewsUsecase
import app.meetacy.backend.usecase.meetings.get.ViewMeetingsUsecase
import app.meetacy.backend.usecase.meetings.history.list.ListMeetingsHistoryUsecase
import app.meetacy.backend.usecase.meetings.map.list.ListMeetingsMapUsecase
import app.meetacy.backend.usecase.meetings.participants.list.ListMeetingParticipantsUsecase
import app.meetacy.backend.usecase.meetings.participate.ParticipateMeetingUsecase
import app.meetacy.backend.usecase.notification.GetNotificationsUsecase
import app.meetacy.backend.usecase.notification.ReadNotificationsUsecase
import app.meetacy.backend.usecase.types.*
import app.meetacy.backend.usecase.users.edit.EditUserUsecase
import app.meetacy.backend.usecase.users.get.GetUsersViewsUsecase
import app.meetacy.backend.usecase.users.get.ViewUserUsecase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import java.io.File
class MockStorage : GenerateTokenUsecase.Storage, LinkEmailUsecase.Storage, AuthRepository,
    ConfirmEmailUsecase.Storage, GetUsersViewsRepository, GetUsersViewsUsecase.Storage,
    GetUsersViewsUsecase.ViewUserRepository, AddFriendUsecase.Storage, ListFriendsUsecase.Storage,
    DeleteFriendUsecase.Storage, ListMeetingsHistoryUsecase.Storage, GetMeetingsViewsRepository,
    CreateMeetingUsecase.Storage, CreateMeetingUsecase.ViewMeetingRepository,
    ParticipateMeetingUsecase.Storage, FilesRepository, DeleteMeetingUsecase.Storage, GetNotificationsUsecase.Storage,
    ReadNotificationsUsecase.Storage, GetFileRepository, ViewMeetingsUsecase.Storage, ListMeetingsHistoryRepository,
    ViewMeetingsRepository, GetMeetingsViewsUsecase.MeetingsProvider,
    ListMeetingsMapUsecase.Storage, EditMeetingUsecase.Storage, EditUserUsecase.Storage,
    ListMeetingParticipantsUsecase.Storage, CheckMeetingRepository, UploadFileUsecase.Storage,
    LocationFlowStorage.Underlying, BaseFriendsLocationStreamingStorage.Storage,
    CreateInvitationUsecase.Storage, GetInvitationsViewsRepository, ReadInvitationUsecase.Storage,
    AcceptInvitationUsecase.Storage, DenyInvitationUsecase.Storage, UpdateInvitationUsecase.Storage,
    CancelInvitationUsecase.Storage {

    private val users = mutableListOf<User>()

    override suspend fun createUser(nickname: String): UserId {
        synchronized(lock = this) {
            val userId = UserId(users.size.toLong())
            val accessHash = AccessHash(DefaultHashGenerator.generate())
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
        filesRepository = this
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
                    FullUser(identity, nickname, email, emailVerified, avatarId)
                }
            }
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

    private val baseDir = File(
        /* parent = */ System.getenv("user.dir"),
        /* child = */ "files"
    ).apply { mkdirs() }.absolutePath

    override suspend fun getFile(fileId: FileIdentity): GetFileResult {
        val file = files.firstOrNull { it.identity == fileId }
            ?: return GetFileResult.InvalidFileIdentity

        return GetFileResult.Success(
            file = File(baseDir, "${fileId.id.long}"),
            fileName = file.fileName,
            fileSize = file.size ?: return GetFileResult.InvalidFileIdentity
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
    ): PagingResult<List<UserId>> = synchronized(this) {
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
    ): PagingResult<List<MeetingId>> = synchronized(this) {
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

    override suspend fun update(invitationId: InvitationId, expiryDate: DateTime?, meetingId: MeetingId?): Boolean {
        if (invitations.indexOfFirst { it.id == invitationId } == -1) return false
        val invitation = invitations[invitations.indexOfFirst { it.id == invitationId }]
        invitations[invitations.indexOfFirst { it.id == invitationId }] = DatabaseInvitation(
            invitation.identity,
            expiryDate ?: invitation.expiryDate,
            invitation.invitedUserId,
            invitation.invitorUserId,
            meetingId ?: invitation.meeting,
            invitation.isAccepted
        )
        return true
    }

    override suspend fun getInvitationOrNull(id: InvitationId): FullInvitation? {
        TODO("Not yet implemented")
    }

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
        invitations[invitations.indexOfFirst { it.id == id }] = DatabaseInvitation(
            invitation.identity,
            invitation.expiryDate,
            invitation.invitedUserId,
            invitation.invitorUserId,
            invitation.meeting,
            isAccepted = true
        )
    }

    override suspend fun addToMeeting(id: MeetingId, userId: UserId) =
        addParticipant(userId, id)

    override suspend fun getList(
        accessIdentity: AccessIdentity,
        amount: Amount,
        pagingId: PagingId?
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
        nickname: String?,
        avatarId: Optional<FileId?>
    ): FullUser {
        synchronized(this) {
            users.replaceAll { user ->
                if (user.identity.id != userId) return@replaceAll user

                user.copy(
                    nickname = nickname ?: user.nickname,
                    avatarId = if (avatarId is Optional.Present) avatarId.value else user.avatarId
                )
            }
        }
        return getUsers(listOf(userId)).first()!!
    }

    override suspend fun viewUser(viewerId: UserId, user: FullUser): UserView =
        viewUserUsecase.viewUser(viewerId, user)

    override suspend fun checkMeetingIdentity(identity: MeetingIdentity): Boolean =
        meetings.any { it.identity == identity }

    override suspend fun getMeetingParticipants(
        meetingId: MeetingId,
        amount: Amount,
        pagingId: PagingId?
    ): PagingResult<List<UserId>> = synchronized(this) {
        val participants = participants
            .reversed()
            .filter { (_, _, id) -> id == meetingId }

        PagingResult(
            data = participants.map { it.second },
            nextPagingId = if (participants.size == amount.int) participants.last().first else null
        )
    }

    private val locations = mutableMapOf<UserId, LocationSnapshot>()

    override suspend fun setLocation(userId: UserId, location: Location) = synchronized(location) {
        locations[userId] = LocationSnapshot(location, DateTime.now())
    }

    override suspend fun getLocation(userId: UserId): LocationSnapshot? = synchronized(locations) {
        return locations[userId]
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

    private val invitations: MutableList<DatabaseInvitation> = mutableListOf()

    override suspend fun isSubscriberOf(subscriberId: UserId, authorId: UserId): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getMeeting(meetingId: MeetingId): FullMeeting? {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(id: UserId): FullUser? {
        TODO("Not yet implemented")
    }

    override suspend fun getInvitationsFrom(authorId: UserId): List<FullInvitation> {
        TODO("Not yet implemented")
    }

    override suspend fun createInvitation(
        accessHash: AccessHash,
        invitedUserId: UserId,
        invitorUserId: UserId,
        expiryDate: DateTime,
        meetingId: MeetingId
    ): InvitationId {
        TODO("Not yet implemented")
    }

    override suspend fun getInvitationViewOrNull(viewerId: UserId, invitation: FullInvitation): InvitationView? {
        TODO("Not yet implemented")
    }

    override suspend fun getInvitationViewOrNull(viewerId: UserId, invitation: InvitationId): InvitationView? {
        TODO("Not yet implemented")
    }

    override suspend fun getInvitationView(viewerId: UserId, invitation: FullInvitation): InvitationView {
        TODO("Not yet implemented")
    }

    override suspend fun getInvitationView(viewerId: UserId, invitation: InvitationId): InvitationView {
        TODO("Not yet implemented")
    }

    override suspend fun getInvitations(invited: UserId): List<FullInvitation> {
        TODO("Not yet implemented")
    }

    override suspend fun getInvitations(from: List<UserId>, to: UserId): List<FullInvitation> {
        TODO("Not yet implemented")
    }

    override suspend fun getInvitationsByIds(ids: List<InvitationId>): List<FullInvitation> {
        TODO("Not yet implemented")
    }

    override suspend fun getFullUser(id: UserId): FullUser? {
        TODO("Not yet implemented")
    }

    override suspend fun cancel(id: InvitationId): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getInvitation(id: InvitationId): FullInvitation? {
        TODO("Not yet implemented")
    }

    override suspend fun markAsDenied(id: InvitationId): Boolean {
        TODO("Not yet implemented")
    }
}

