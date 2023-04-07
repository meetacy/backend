import app.meetacy.backend.endpoint.auth.AuthDependencies
import app.meetacy.backend.endpoint.auth.email.EmailDependencies
import app.meetacy.backend.endpoint.files.FilesDependencies
import app.meetacy.backend.endpoint.friends.FriendsDependencies
import app.meetacy.backend.endpoint.meetings.MeetingsDependencies
import app.meetacy.backend.endpoint.meetings.avatar.MeetingAvatarDependencies
import app.meetacy.backend.endpoint.meetings.history.MeetingsHistoryDependencies
import app.meetacy.backend.endpoint.meetings.map.MeetingsMapDependencies
import app.meetacy.backend.endpoint.notifications.NotificationsDependencies
import app.meetacy.backend.endpoint.startEndpoints
import app.meetacy.backend.endpoint.users.UsersDependencies
import app.meetacy.backend.endpoint.users.avatar.UserAvatarDependencies
import app.meetacy.backend.hash.integration.DefaultHashGenerator
import app.meetacy.backend.usecase.auth.GenerateTokenUsecase
import app.meetacy.backend.usecase.email.ConfirmEmailUsecase
import app.meetacy.backend.usecase.email.LinkEmailUsecase
import app.meetacy.backend.usecase.friends.add.AddFriendUsecase
import app.meetacy.backend.usecase.friends.delete.DeleteFriendUsecase
import app.meetacy.backend.usecase.friends.list.ListFriendsUsecase
import app.meetacy.backend.usecase.integration.auth.UsecaseTokenGenerateRepository
import app.meetacy.backend.usecase.integration.email.confirm.UsecaseConfirmEmailRepository
import app.meetacy.backend.usecase.integration.email.link.UsecaseLinkEmailRepository
import app.meetacy.backend.usecase.integration.friends.add.UsecaseAddFriendRepository
import app.meetacy.backend.usecase.integration.friends.delete.UsecaseDeleteFriendRepository
import app.meetacy.backend.usecase.integration.friends.get.UsecaseListFriendsRepository
import app.meetacy.backend.usecase.integration.meetings.avatar.add.UsecaseAddMeetingAvatarRepository
import app.meetacy.backend.usecase.integration.meetings.avatar.delete.UsecaseDeleteMeetingAvatarRepository
import app.meetacy.backend.usecase.integration.meetings.create.UsecaseCreateMeetingRepository
import app.meetacy.backend.usecase.integration.meetings.delete.UsecaseDeleteMeetingRepository
import app.meetacy.backend.usecase.integration.meetings.get.UsecaseGetMeetingRepository
import app.meetacy.backend.usecase.integration.meetings.history.list.UsecaseListMeetingsHistoryRepository
import app.meetacy.backend.usecase.integration.meetings.map.list.UsecaseListMeetingsMapRepository
import app.meetacy.backend.usecase.integration.meetings.participate.UsecaseParticipateMeetingRepository
import app.meetacy.backend.usecase.integration.notifications.get.UsecaseGetNotificationsRepository
import app.meetacy.backend.usecase.integration.notifications.read.UsecaseReadNotificationsRepository
import app.meetacy.backend.usecase.integration.users.avatar.add.UsecaseAddUserAvatarRepository
import app.meetacy.backend.usecase.integration.users.avatar.delete.UsecaseDeleteUserAvatarRepository
import app.meetacy.backend.usecase.integration.users.get.UsecaseUserRepository
import app.meetacy.backend.usecase.meetings.avatar.add.AddMeetingAvatarUsecase
import app.meetacy.backend.usecase.meetings.avatar.delete.DeleteMeetingAvatarUsecase
import app.meetacy.backend.usecase.meetings.create.CreateMeetingUsecase
import app.meetacy.backend.usecase.meetings.delete.DeleteMeetingUsecase
import app.meetacy.backend.usecase.meetings.get.GetMeetingUsecase
import app.meetacy.backend.usecase.meetings.history.list.ListMeetingsHistoryUsecase
import app.meetacy.backend.usecase.meetings.map.list.ListMeetingsMapUsecase
import app.meetacy.backend.usecase.meetings.participate.ParticipateMeetingUsecase
import app.meetacy.backend.usecase.notification.GetNotificationsUsecase
import app.meetacy.backend.usecase.notification.ReadNotificationsUsecase
import app.meetacy.backend.usecase.users.avatar.add.AddUserAvatarUsecase
import app.meetacy.backend.usecase.users.avatar.delete.DeleteUserAvatarUsecase
import app.meetacy.backend.usecase.users.get.GetUserSafeUsecase
import app.meetacy.backend.utf8.integration.DefaultUtf8Checker
import app.meetacy.sdk.MeetacyApi
import app.meetacy.sdk.users.AuthorizedSelfUserRepository
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.logging.*
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest

val testApi = MeetacyApi(
    baseUrl = "http://localhost:8080",
    httpClient = HttpClient {
        Logging {
//            level = LogLevel.NONE
            level = LogLevel.ALL
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
                    utf8Checker = DefaultUtf8Checker
                )
            ),
            participateMeetingRepository = UsecaseParticipateMeetingRepository(
                usecase = ParticipateMeetingUsecase(
                    authRepository = mockStorage,
                    storage = mockStorage,
                    getMeetingsViewsRepository = mockStorage
                )
            ),
            addMeetingAvatarDependencies = MeetingAvatarDependencies(
                addMeetingAvatarRepository = UsecaseAddMeetingAvatarRepository(
                    usecase = AddMeetingAvatarUsecase(
                        authRepository = mockStorage,
                        filesRepository = mockStorage,
                        getMeetingsViewsRepository = mockStorage,
                        storage = mockStorage
                    )
                ),
                deleteMeetingAvatarRepository = UsecaseDeleteMeetingAvatarRepository(
                    usecase = DeleteMeetingAvatarUsecase(
                        authRepository = mockStorage,
                        getMeetingsViewsRepository = mockStorage,
                        storage = mockStorage
                    )
                )
            ),
            deleteMeetingRepository = UsecaseDeleteMeetingRepository(
                usecase = DeleteMeetingUsecase(
                    authRepository = mockStorage,
                    getMeetingsViewsRepository = mockStorage,
                    storage = mockStorage
                )
            ),
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
            saveFileRepository = mockStorage,
            getFileRepository = mockStorage
        ),
        usersDependencies = UsersDependencies(
            getUserRepository = UsecaseUserRepository(
                usecase = GetUserSafeUsecase(
                    authRepository = mockStorage,
                    usersViewsRepository = mockStorage
                )
            ),
            addUserAvatarDependencies = UserAvatarDependencies(
                addUserAvatarRepository = UsecaseAddUserAvatarRepository(
                    usecase = AddUserAvatarUsecase(
                        authRepository = mockStorage,
                        filesRepository = mockStorage,
                        storage = mockStorage
                    )
                ),
                deleteUserAvatarRepository = UsecaseDeleteUserAvatarRepository(
                    usecase = DeleteUserAvatarUsecase(
                        authRepository = mockStorage,
                        storage = mockStorage
                    )
                )
            )
        )
    )
    block()
    server.stop()
}
