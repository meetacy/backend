
import app.meetacy.backend.endpoint.auth.AuthDependencies
import app.meetacy.backend.endpoint.auth.email.EmailDependencies
import app.meetacy.backend.endpoint.files.FilesDependencies
import app.meetacy.backend.endpoint.friends.FriendsDependencies
import app.meetacy.backend.endpoint.friends.location.FriendsLocationDependencies
import app.meetacy.backend.endpoint.invitations.InvitationsDependencies
import app.meetacy.backend.endpoint.meetings.MeetingsDependencies
import app.meetacy.backend.endpoint.meetings.history.MeetingsHistoryDependencies
import app.meetacy.backend.endpoint.meetings.map.MeetingsMapDependencies
import app.meetacy.backend.endpoint.meetings.participants.ParticipantsDependencies
import app.meetacy.backend.endpoint.notifications.NotificationsDependencies
import app.meetacy.backend.endpoint.startEndpoints
import app.meetacy.backend.endpoint.users.UsersDependencies
import app.meetacy.backend.hash.integration.DefaultHashGenerator
import app.meetacy.backend.usecase.auth.GenerateTokenUsecase
import app.meetacy.backend.usecase.email.ConfirmEmailUsecase
import app.meetacy.backend.usecase.email.LinkEmailUsecase
import app.meetacy.backend.usecase.files.UploadFileUsecase
import app.meetacy.backend.usecase.friends.add.AddFriendUsecase
import app.meetacy.backend.usecase.friends.delete.DeleteFriendUsecase
import app.meetacy.backend.usecase.friends.list.ListFriendsUsecase
import app.meetacy.backend.usecase.integration.auth.UsecaseTokenGenerateRepository
import app.meetacy.backend.usecase.integration.email.confirm.UsecaseConfirmEmailRepository
import app.meetacy.backend.usecase.integration.email.link.UsecaseLinkEmailRepository
import app.meetacy.backend.usecase.integration.files.UsecaseUploadFileRepository
import app.meetacy.backend.usecase.integration.friends.add.UsecaseAddFriendRepository
import app.meetacy.backend.usecase.integration.friends.delete.UsecaseDeleteFriendRepository
import app.meetacy.backend.usecase.integration.friends.get.UsecaseListFriendsRepository
import app.meetacy.backend.usecase.integration.friends.location.stream.UsecaseStreamLocationRepository
import app.meetacy.backend.usecase.integration.invitations.accept.UsecaseAcceptInvitationRepository
import app.meetacy.backend.usecase.integration.invitations.cancel.UsecaseCancelInvitationRepository
import app.meetacy.backend.usecase.integration.invitations.create.UsecaseCreateInvitationRepository
import app.meetacy.backend.usecase.integration.invitations.deny.UsecaseDenyInvitationRepository
import app.meetacy.backend.usecase.integration.invitations.read.UsecaseReadInvitationRepository
import app.meetacy.backend.usecase.integration.invitations.update.UsecaseUpdateInvitationRepository
import app.meetacy.backend.usecase.integration.meetings.create.UsecaseCreateMeetingRepository
import app.meetacy.backend.usecase.integration.meetings.delete.UsecaseDeleteMeetingRepository
import app.meetacy.backend.usecase.integration.meetings.edit.UsecaseEditMeetingRepository
import app.meetacy.backend.usecase.integration.meetings.get.UsecaseGetMeetingRepository
import app.meetacy.backend.usecase.integration.meetings.history.list.UsecaseListMeetingsHistoryRepository
import app.meetacy.backend.usecase.integration.meetings.map.list.UsecaseListMeetingsMapRepository
import app.meetacy.backend.usecase.integration.meetings.participants.list.UsecaseListMeetingParticipantsRepository
import app.meetacy.backend.usecase.integration.meetings.participate.UsecaseParticipateMeetingRepository
import app.meetacy.backend.usecase.integration.notifications.get.UsecaseGetNotificationsRepository
import app.meetacy.backend.usecase.integration.notifications.read.UsecaseReadNotificationsRepository
import app.meetacy.backend.usecase.integration.users.edit.UsecaseEditUserRepository
import app.meetacy.backend.usecase.integration.users.get.UsecaseUserRepository
import app.meetacy.backend.usecase.integration.users.validate.UsecaseValidateUsernameRepository
import app.meetacy.backend.usecase.invitations.accept.AcceptInvitationUsecase
import app.meetacy.backend.usecase.invitations.cancel.CancelInvitationUsecase
import app.meetacy.backend.usecase.invitations.create.CreateInvitationUsecase
import app.meetacy.backend.usecase.invitations.deny.DenyInvitationUsecase
import app.meetacy.backend.usecase.invitations.read.ReadInvitationUsecase
import app.meetacy.backend.usecase.invitations.update.UpdateInvitationUsecase
import app.meetacy.backend.usecase.location.stream.BaseFriendsLocationStreamingStorage
import app.meetacy.backend.usecase.location.stream.FriendsLocationStreamingUsecase
import app.meetacy.backend.usecase.meetings.create.CreateMeetingUsecase
import app.meetacy.backend.usecase.meetings.delete.DeleteMeetingUsecase
import app.meetacy.backend.usecase.meetings.edit.EditMeetingUsecase
import app.meetacy.backend.usecase.meetings.get.GetMeetingUsecase
import app.meetacy.backend.usecase.meetings.history.list.ListMeetingsHistoryUsecase
import app.meetacy.backend.usecase.meetings.map.list.ListMeetingsMapUsecase
import app.meetacy.backend.usecase.meetings.participants.list.ListMeetingParticipantsUsecase
import app.meetacy.backend.usecase.meetings.participate.ParticipateMeetingUsecase
import app.meetacy.backend.usecase.notification.GetNotificationsUsecase
import app.meetacy.backend.usecase.notification.ReadNotificationsUsecase
import app.meetacy.backend.usecase.users.edit.EditUserUsecase
import app.meetacy.backend.usecase.users.get.GetUserSafeUsecase
import app.meetacy.backend.usecase.validate.ValidateUsernameUsecase
import app.meetacy.backend.utf8.integration.DefaultUtf8Checker
import app.meetacy.sdk.MeetacyApi
import app.meetacy.sdk.meetings.AuthorizedMeetingsApi
import app.meetacy.sdk.types.auth.Token
import app.meetacy.sdk.types.datetime.Date
import app.meetacy.sdk.types.location.Location
import app.meetacy.sdk.types.url.url
import app.meetacy.sdk.users.AuthorizedSelfUserRepository
import io.ktor.client.*
import io.ktor.client.plugins.logging.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import java.io.File

val testApi = MeetacyApi(
    baseUrl = "http://localhost:8080".url,
    httpClient = HttpClient {
        Logging {
            level = LogLevel.NONE
//            level = LogLevel.ALL
        }
        expectSuccess = true
        developmentMode = true
    }
)

suspend fun generateTestAccount(
    postfix: String? = null
): AuthorizedSelfUserRepository {
    val newClient = testApi.auth.generateAuthorizedApi(
        nickname = listOfNotNull("Test Account", postfix)
            .joinToString(separator = " ")
    )

    return newClient.getMe()
}

val InvalidToken: Token = Token("1:_INVALID_TOKEN_qD3Z0uM0iqE7g1J8VxkuzGe0CAXXDyHdfUGmj2xBPhuMYGFcHVawNvrK1KB9F9rgoeLa8Go2lqPDnzKYJg4EFbJUyQ6qu6P3iGg5Ytl4w1tpO1nja1aFxNtneq07uFERxSSsR7jd5YAe1Y0urlx9KDKxoQdIdGVvWGuc7dv3IStQUCZQziSmzjuxrVrUF9ywvg1bM8GiR2TU5nUItRPDhDyebeMzQcC7vwRYTdbUIIh4dYX4y")

fun InvalidId(id: String): String = "$id:_INVALID_ID_qD3qD3Z0uM0iqE7g1J8VxkuzGe0CAXXDyHdfUGmj2xBPhuMYGFcHVawNvrK1KB9F9rgoeLa8Go2lqPDnzKYJg4EFbJUyQ6qu6P3iGg5Ytl4w1tpO1nja1aFxNtneq07uFERxSSsR7jd5YAe1Y0urlx9KDKxoQdIdGVvWGuc7dv3IStQUCZQziSmzjuxrVrUF9ywvg1bM8GiR2TU5nUItRPDhDyebeMzQcC7vwRYTdbUIIh4dYX4y"

suspend fun AuthorizedMeetingsApi.createTestMeeting(title: String = "Test Meeting") =
    create(
        title = title,
        date = Date.today(),
        location = Location.NullIsland
    )

@OptIn(ExperimentalCoroutinesApi::class)
fun runTestServer(
    port: Int = 8080,
    wait: Boolean = false,
    block: suspend TestScope.() -> Unit
) = runTest {
    val mockStorage = MockStorage()

    val server = startEndpoints(
        port = port,
        wait = wait,
        authDependencies = AuthDependencies(
            emailDependencies = EmailDependencies(
                linkEmailRepository = UsecaseLinkEmailRepository(
                    usecase = LinkEmailUsecase(
                        storage = mockStorage,
                        hashGenerator = DefaultHashGenerator,
                        authRepository = mockStorage,
                        mailer = object : LinkEmailUsecase.Mailer {
                            override fun sendEmailOccupiedMessage(email: String) {}
                            override fun sendConfirmationMessage(email: String, confirmationHash: String) {}
                        }
                    )
                ),
                confirmEmailRepository = UsecaseConfirmEmailRepository(
                    usecase = ConfirmEmailUsecase(
                        storage = mockStorage
                    )
                )
            ),
            tokenGenerateRepository = UsecaseTokenGenerateRepository(
                usecase = GenerateTokenUsecase(
                    storage = mockStorage,
                    tokenGenerator = DefaultHashGenerator,
                    utf8Checker = DefaultUtf8Checker
                )
            )
        ),
        friendsDependencies = FriendsDependencies(
            friendsLocationDependencies = FriendsLocationDependencies(
                streamLocationRepository = UsecaseStreamLocationRepository(
                    usecase = FriendsLocationStreamingUsecase(
                        authRepository = mockStorage,
                        storage = BaseFriendsLocationStreamingStorage(
                            flowStorageUnderlying = mockStorage,
                            friendsStorage = mockStorage
                        ),
                        usersViewsRepository = mockStorage
                    )
                )
            ),
            addFriendRepository = UsecaseAddFriendRepository(
                usecase = AddFriendUsecase(
                    authRepository = mockStorage,
                    getUsersViewsRepository = mockStorage,
                    storage = mockStorage
                )
            ),
            listFriendsRepository = UsecaseListFriendsRepository(
                usecase = ListFriendsUsecase(
                    authRepository = mockStorage,
                    storage = mockStorage,
                    getUsersViewsRepository = mockStorage
                )
            ),
            deleteFriendRepository = UsecaseDeleteFriendRepository(
                usecase = DeleteFriendUsecase(
                    authRepository = mockStorage,
                    storage = mockStorage,
                    getUsersViewsRepository = mockStorage
                )
            )
        ),
        meetingsDependencies = MeetingsDependencies(
            meetingsHistoryDependencies = MeetingsHistoryDependencies(
                listMeetingsHistoryRepository = UsecaseListMeetingsHistoryRepository(
                    usecase = ListMeetingsHistoryUsecase(
                        authRepository = mockStorage,
                        storage = mockStorage,
                        getMeetingsViewsRepository = mockStorage
                    )
                )
            ),
            meetingsMapDependencies = MeetingsMapDependencies(
                listMeetingsMapRepository = UsecaseListMeetingsMapRepository(
                    usecase = ListMeetingsMapUsecase(
                        authRepository = mockStorage,
                        storage = mockStorage,
                        getMeetingsViewsRepository = mockStorage,
                        viewMeetingsRepository = mockStorage
                    )
                )
            ),
            meetingParticipantsDependencies = ParticipantsDependencies(
                listMeetingParticipantsRepository = UsecaseListMeetingParticipantsRepository(
                    usecase = ListMeetingParticipantsUsecase(
                        authRepository = mockStorage,
                        checkMeetingRepository = mockStorage,
                        storage = mockStorage,
                        getUsersViewsRepository = mockStorage
                    )
                )
            ),
            getMeetingRepository = UsecaseGetMeetingRepository(
                usecase = GetMeetingUsecase(
                    authRepository = mockStorage,
                    getMeetingsViewsRepository = mockStorage
                )
            ),
            createMeetingRepository = UsecaseCreateMeetingRepository(
                usecase = CreateMeetingUsecase(
                    hashGenerator = DefaultHashGenerator,
                    storage = mockStorage,
                    authRepository = mockStorage,
                    viewMeetingRepository = mockStorage,
                    utf8Checker = DefaultUtf8Checker,
                    filesRepository = mockStorage
                )
            ),
            participateMeetingRepository = UsecaseParticipateMeetingRepository(
                usecase = ParticipateMeetingUsecase(
                    authRepository = mockStorage,
                    storage = mockStorage,
                    getMeetingsViewsRepository = mockStorage
                )
            ),
            deleteMeetingRepository = UsecaseDeleteMeetingRepository(
                usecase = DeleteMeetingUsecase(
                    authRepository = mockStorage,
                    getMeetingsViewsRepository = mockStorage,
                    storage = mockStorage
                )
            ),
            editMeetingRepository = UsecaseEditMeetingRepository(
                usecase = EditMeetingUsecase(
                    storage = mockStorage,
                    authRepository = mockStorage,
                    getMeetingsViewsRepository = mockStorage,
                    viewMeetingsRepository = mockStorage,
                    filesRepository = mockStorage,
                    utf8Checker = DefaultUtf8Checker
                )
            )
        ),
        notificationsDependencies = NotificationsDependencies(
            getNotificationsRepository = UsecaseGetNotificationsRepository(
                usecase = GetNotificationsUsecase(
                    authRepository = mockStorage,
                    usersRepository = mockStorage,
                    meetingsRepository = mockStorage,
                    storage = mockStorage
                )
            ),
            readNotificationsRepository = UsecaseReadNotificationsRepository(
                usecase = ReadNotificationsUsecase(
                    authRepository = mockStorage,
                    storage = mockStorage
                )
            )
        ),
        filesDependencies = FilesDependencies(
            saveFileRepository = UsecaseUploadFileRepository(
                basePath = File(
                    /* parent = */ System.getenv("user.dir"),
                    /* child = */ "files"
                ).apply {
                    mkdirs()
                    deleteOnExit()
                }.absolutePath,
                usecase = UploadFileUsecase(
                    authRepository = mockStorage,
                    storage = mockStorage,
                    hashGenerator = DefaultHashGenerator
                ),
                filesLimit = 100L * 1024 * 1024,
                deleteFilesOnExit = true
            ),
            getFileRepository = mockStorage
        ),
        usersDependencies = UsersDependencies(
            getUserRepository = UsecaseUserRepository(
                usecase = GetUserSafeUsecase(
                    authRepository = mockStorage,
                    usersViewsRepository = mockStorage
                )
            ),

            editUserRepository = UsecaseEditUserRepository(
                usecase = EditUserUsecase(
                    storage = mockStorage,
                    authRepository = mockStorage,
                    filesRepository = mockStorage,
                    utf8Checker = DefaultUtf8Checker
                )
            )
        ),
        invitationsDependencies = InvitationsDependencies(
            invitationsCreateRepository = UsecaseCreateInvitationRepository(
                usecase = CreateInvitationUsecase(
                    authRepository = mockStorage,
                    storage = mockStorage,
                    hashGenerator = DefaultHashGenerator,
                    getInvitationsViewsRepository = mockStorage
                )
            ),
            invitationsGetRepository = UsecaseReadInvitationRepository(
                usecase = ReadInvitationUsecase(
                    storage = mockStorage,
                    authRepository = mockStorage,
                    getInvitationsViewsRepository = mockStorage
                )
            ),
            invitationsAcceptRepository = UsecaseAcceptInvitationRepository(
                usecase = AcceptInvitationUsecase(
                    authRepository = mockStorage,
                    storage = mockStorage
                )
            ),
            invitationsDenyRepository = UsecaseDenyInvitationRepository(
                usecase = DenyInvitationUsecase(
                    authRepository = mockStorage,
                    storage = mockStorage
                ),
            ),
            invitationUpdateRepository = UsecaseUpdateInvitationRepository(
                usecase = UpdateInvitationUsecase(
                    authRepository = mockStorage,
                    storage = mockStorage,
                    getInvitationsViewsRepository = mockStorage
                ),
            ),
            invitationCancelRepository = UsecaseCancelInvitationRepository(
                usecase = CancelInvitationUsecase(
                    authRepository = mockStorage,
                    storage = mockStorage
                ),
            ),
        ),
        validateUsernameRepository = UsecaseValidateUsernameRepository(
            usecase = ValidateUsernameUsecase(
                validateRepository = mockStorage
            )
        )
    )
    block()
    server.stop()
}
