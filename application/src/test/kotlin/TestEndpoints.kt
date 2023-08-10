
import app.meetacy.backend.database.integration.types.UsecaseGetNotificationsViewsRepository
import app.meetacy.backend.endpoint.friends.FriendsDependencies
import app.meetacy.backend.endpoint.friends.location.FriendsLocationDependencies
import app.meetacy.backend.endpoint.invitations.InvitationsDependencies
import app.meetacy.backend.endpoint.notifications.NotificationsDependencies
import app.meetacy.backend.endpoint.prepareEndpoints
import app.meetacy.backend.endpoint.updates.UpdatesDependencies
import app.meetacy.backend.types.integration.generator.BasicHashGenerator
import app.meetacy.backend.usecase.friends.add.AddFriendUsecase
import app.meetacy.backend.usecase.friends.delete.DeleteFriendUsecase
import app.meetacy.backend.usecase.friends.list.ListFriendsUsecase
import app.meetacy.backend.usecase.integration.friends.add.UsecaseAddFriendRepository
import app.meetacy.backend.usecase.integration.friends.delete.UsecaseDeleteFriendRepository
import app.meetacy.backend.usecase.integration.friends.get.UsecaseListFriendsRepository
import app.meetacy.backend.usecase.integration.friends.location.stream.UsecaseStreamLocationRepository
import app.meetacy.backend.usecase.integration.invitations.accept.UsecaseAcceptInvitationRepository
import app.meetacy.backend.usecase.integration.invitations.cancel.UsecaseCancelInvitationRepository
import app.meetacy.backend.usecase.integration.invitations.create.UsecaseCreateInvitationRepository
import app.meetacy.backend.usecase.integration.invitations.deny.UsecaseDenyInvitationRepository
import app.meetacy.backend.usecase.integration.notifications.get.UsecaseListNotificationsRepository
import app.meetacy.backend.usecase.integration.notifications.read.UsecaseReadNotificationsRepository
import app.meetacy.backend.usecase.integration.updates.stream.StreamUpdatesRepository
import app.meetacy.backend.usecase.integration.users.validate.UsecaseValidateUsernameRepository
import app.meetacy.backend.usecase.invitations.accept.AcceptInvitationUsecase
import app.meetacy.backend.usecase.invitations.cancel.CancelInvitationUsecase
import app.meetacy.backend.usecase.invitations.create.CreateInvitationUsecase
import app.meetacy.backend.usecase.invitations.deny.DenyInvitationUsecase
import app.meetacy.backend.usecase.location.stream.FriendsLocationStreamingUsecase
import app.meetacy.backend.usecase.notifications.ReadNotificationsUsecase
import app.meetacy.backend.usecase.notifications.get.GetNotificationsUsecase
import app.meetacy.backend.usecase.notifications.get.GetNotificationsViewsUsecase
import app.meetacy.backend.usecase.users.validate.ValidateUsernameUsecase
import app.meetacy.sdk.MeetacyApi
import app.meetacy.sdk.meetings.AuthorizedMeetingsApi
import app.meetacy.sdk.types.annotation.UnstableApi
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

@OptIn(UnstableApi::class)
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

    val server = prepareEndpoints(
        port = port,
        friendsDependencies = FriendsDependencies(
            friendsLocationDependencies = FriendsLocationDependencies(
                streamLocationRepository = UsecaseStreamLocationRepository(
                    usecase = FriendsLocationStreamingUsecase(
                        authRepository = mockStorage,
                        storage = mockStorage,
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
        notificationsDependencies = NotificationsDependencies(
            listNotificationsRepository = UsecaseListNotificationsRepository(
                usecase = GetNotificationsUsecase(
                    authRepository = mockStorage,
                    storage = mockStorage,
                    viewNotificationsRepository = mockStorage
                )
            ),
            readNotificationsRepository = UsecaseReadNotificationsRepository(
                usecase = ReadNotificationsUsecase(
                    authRepository = mockStorage,
                    storage = mockStorage
                )
            )
        ),
        invitationsDependencies = InvitationsDependencies(
            invitationsCreateRepository = UsecaseCreateInvitationRepository(
                usecase = CreateInvitationUsecase(
                    authRepository = mockStorage,
                    storage = mockStorage,
                    hashGenerator = BasicHashGenerator,
                    invitationsRepository = mockStorage
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
        ),
        updatesDependencies = UpdatesDependencies(
            streamUpdatesRepository = StreamUpdatesRepository(
                auth = mockStorage,
                storage = mockStorage,
                notificationsRepository = UsecaseGetNotificationsViewsRepository(
                    usecase = GetNotificationsViewsUsecase(
                        storage = mockStorage,
                        viewRepository = mockStorage
                    )
                )
            )
        )
    ).start(wait)
    // TODO: migrate this fuckery to the new DI
    block()
    server.stop()
}
