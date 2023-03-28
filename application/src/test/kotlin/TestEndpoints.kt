import app.meetacy.api.AuthorizedMeetacyApi
import app.meetacy.api.MeetacyApi
import app.meetacy.backend.endpoint.auth.AuthDependencies
import app.meetacy.backend.endpoint.auth.email.EmailDependencies
import app.meetacy.backend.endpoint.files.FilesDependencies
import app.meetacy.backend.endpoint.friends.FriendsDependencies
import app.meetacy.backend.endpoint.meetings.MeetingsDependencies
import app.meetacy.backend.endpoint.meetings.avatar.MeetingAvatarDependencies
import app.meetacy.backend.endpoint.meetings.history.MeetingsHistoryDependencies
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
import app.meetacy.backend.usecase.integration.meetings.list.UsecaseListMeetingsHistoryRepository
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
import app.meetacy.backend.usecase.meetings.participate.ParticipateMeetingUsecase
import app.meetacy.backend.usecase.notification.GetNotificationsUsecase
import app.meetacy.backend.usecase.notification.ReadNotificationsUsecase
import app.meetacy.backend.usecase.users.avatar.add.AddUserAvatarUsecase
import app.meetacy.backend.usecase.users.avatar.delete.DeleteUserAvatarUsecase
import app.meetacy.backend.usecase.users.get.GetUserSafeUsecase
import app.meetacy.backend.utf8.integration.DefaultUtf8Checker
import app.meetacy.types.user.SelfUser
import io.ktor.client.*
import io.ktor.client.plugins.logging.*

val testApi = MeetacyApi(
    baseUrl = "http://localhost:8080",
    httpClient = HttpClient {
        Logging {
            level = LogLevel.NONE
            level = LogLevel.ALL
        }
    }
)

suspend fun generateTestAccount(
    postfix: String? = null
): Pair<AuthorizedMeetacyApi, SelfUser> {
    val newClient = testApi.auth.generateAuthorizedApi(
        nickname = listOfNotNull("Test Account", postfix)
            .joinToString(separator = " ")
    )

    val me = newClient.getMe()

    return newClient to me
}

fun startTestEndpoints(
    port: Int = 8080,
    wait: Boolean = false
) {
    startEndpoints(
        port = port,
        wait = wait,
        authDependencies = AuthDependencies(
            emailDependencies = EmailDependencies(
                linkEmailRepository = UsecaseLinkEmailRepository(
                    usecase = LinkEmailUsecase(
                        storage = MockStorage,
                        hashGenerator = DefaultHashGenerator,
                        authRepository = MockStorage,
                        mailer = object : LinkEmailUsecase.Mailer {
                            override fun sendEmailOccupiedMessage(email: String) {}
                            override fun sendConfirmationMessage(email: String, confirmationHash: String) {}
                        }
                    )
                ),
                confirmEmailRepository = UsecaseConfirmEmailRepository(
                    usecase = ConfirmEmailUsecase(
                        storage = MockStorage
                    )
                )
            ),
            tokenGenerateRepository = UsecaseTokenGenerateRepository(
                usecase = GenerateTokenUsecase(
                    storage = MockStorage,
                    tokenGenerator = DefaultHashGenerator,
                    utf8Checker = DefaultUtf8Checker
                )
            )
        ),
        friendsDependencies = FriendsDependencies(
            addFriendRepository = UsecaseAddFriendRepository(
                usecase = AddFriendUsecase(
                    authRepository = MockStorage,
                    getUsersViewsRepository = MockStorage,
                    storage = MockStorage
                )
            ),
            listFriendsRepository = UsecaseListFriendsRepository(
                usecase = ListFriendsUsecase(
                    authRepository = MockStorage,
                    storage = MockStorage,
                    getUsersViewsRepository = MockStorage
                )
            ),
            deleteFriendRepository = UsecaseDeleteFriendRepository(
                usecase = DeleteFriendUsecase(
                    authRepository = MockStorage,
                    storage = MockStorage,
                    getUsersViewsRepository = MockStorage
                )
            )
        ),
        meetingsDependencies = MeetingsDependencies(
            meetingsHistoryDependencies = MeetingsHistoryDependencies(
                listMeetingsHistoryRepository = UsecaseListMeetingsHistoryRepository(
                    usecase = ListMeetingsHistoryUsecase(
                        authRepository = MockStorage,
                        storage = MockStorage,
                        getMeetingsViewsRepository = MockStorage
                    )
                )
            ),
            getMeetingRepository = UsecaseGetMeetingRepository(
                usecase = GetMeetingUsecase(
                    authRepository = MockStorage,
                    getMeetingsViewsRepository = MockStorage
                )
            ),
            createMeetingRepository = UsecaseCreateMeetingRepository(
                usecase = CreateMeetingUsecase(
                    hashGenerator = DefaultHashGenerator,
                    storage = MockStorage,
                    authRepository = MockStorage,
                    viewMeetingRepository = MockStorage,
                    utf8Checker = DefaultUtf8Checker
                )
            ),
            participateMeetingRepository = UsecaseParticipateMeetingRepository(
                usecase = ParticipateMeetingUsecase(
                    authRepository = MockStorage,
                    storage = MockStorage,
                    getMeetingsViewsRepository = MockStorage
                )
            ),
            addMeetingAvatarDependencies = MeetingAvatarDependencies(
                addMeetingAvatarRepository = UsecaseAddMeetingAvatarRepository(
                    usecase = AddMeetingAvatarUsecase(
                        authRepository = MockStorage,
                        filesRepository = MockStorage,
                        getMeetingsViewsRepository = MockStorage,
                        storage = MockStorage
                    )
                ),
                deleteMeetingAvatarRepository = UsecaseDeleteMeetingAvatarRepository(
                    usecase = DeleteMeetingAvatarUsecase(
                        authRepository = MockStorage,
                        getMeetingsViewsRepository = MockStorage,
                        storage = MockStorage
                    )
                )
            ),
            deleteMeetingRepository = UsecaseDeleteMeetingRepository(
                usecase = DeleteMeetingUsecase(
                    authRepository = MockStorage,
                    getMeetingsViewsRepository = MockStorage,
                    storage = MockStorage
                )
            ),
        ),
        notificationsDependencies = NotificationsDependencies(
            getNotificationsRepository = UsecaseGetNotificationsRepository(
                usecase = GetNotificationsUsecase(
                    authRepository = MockStorage,
                    usersRepository = MockStorage,
                    meetingsRepository = MockStorage,
                    storage = MockStorage
                )
            ),
            readNotificationsRepository = UsecaseReadNotificationsRepository(
                usecase = ReadNotificationsUsecase(
                    authRepository = MockStorage,
                    storage = MockStorage
                )
            )
        ),
        filesDependencies = FilesDependencies(
            saveFileRepository = MockStorage,
            getFileRepository = MockStorage
        ),
        usersDependencies = UsersDependencies(
            getUserRepository = UsecaseUserRepository(
                usecase = GetUserSafeUsecase(
                    authRepository = MockStorage,
                    usersViewsRepository = MockStorage
                )
            ),
            addUserAvatarDependencies = UserAvatarDependencies(
                addUserAvatarRepository = UsecaseAddUserAvatarRepository(
                    usecase = AddUserAvatarUsecase(
                        authRepository = MockStorage,
                        filesRepository = MockStorage,
                        storage = MockStorage
                    )
                ),
                deleteUserAvatarRepository = UsecaseDeleteUserAvatarRepository(
                    usecase = DeleteUserAvatarUsecase(
                        authRepository = MockStorage,
                        storage = MockStorage
                    )
                )
            )
        )
    )
}
