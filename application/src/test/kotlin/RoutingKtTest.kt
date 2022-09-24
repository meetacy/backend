import app.meetacy.backend.database.integration.email.DatabaseConfirmEmailStorage
import app.meetacy.backend.database.integration.email.DatabaseLinkEmailMailer
import app.meetacy.backend.database.integration.email.DatabaseLinkEmailStorage
import app.meetacy.backend.database.integration.friends.DatabaseAddFriendStorage
import app.meetacy.backend.database.integration.friends.DatabaseGetFriendsStorage
import app.meetacy.backend.database.integration.meetings.create.DatabaseCreateMeetingStorage
import app.meetacy.backend.database.integration.meetings.create.DatabaseCreateMeetingViewMeetingRepository
import app.meetacy.backend.database.integration.meetings.list.DatabaseGetMeetingsListStorage
import app.meetacy.backend.database.integration.meetings.participate.DatabaseParticipateMeetingStorage
import app.meetacy.backend.database.integration.notifications.DatabaseGetNotificationStorage
import app.meetacy.backend.database.integration.notifications.DatabaseReadNotificationsStorage
import app.meetacy.backend.database.integration.tokenGenerator.DatabaseGenerateTokenStorage
import app.meetacy.backend.database.integration.types.DatabaseAuthRepository
import app.meetacy.backend.database.integration.types.DatabaseGetMeetingsViewsRepository
import app.meetacy.backend.database.integration.types.DatabaseGetUsersViewsRepository
import app.meetacy.backend.endpoint.auth.AuthDependencies
import app.meetacy.backend.endpoint.auth.email.EmailDependencies
import app.meetacy.backend.endpoint.friends.FriendsDependencies
import app.meetacy.backend.endpoint.meetings.MeetingsDependencies
import app.meetacy.backend.endpoint.notifications.NotificationsDependencies
import app.meetacy.backend.endpoint.startEndpoints
import app.meetacy.backend.hash.integration.DefaultHashGenerator
import app.meetacy.backend.usecase.auth.GenerateTokenUsecase
import app.meetacy.backend.usecase.email.ConfirmEmailUsecase
import app.meetacy.backend.usecase.email.LinkEmailUsecase
import app.meetacy.backend.usecase.friends.add.AddFriendUsecase
import app.meetacy.backend.usecase.friends.get.GetFriendsUsecase
import app.meetacy.backend.usecase.integration.auth.UsecaseTokenGenerateRepository
import app.meetacy.backend.usecase.integration.email.confirm.UsecaseConfirmEmailRepository
import app.meetacy.backend.usecase.integration.email.link.UsecaseLinkEmailRepository
import app.meetacy.backend.usecase.integration.friends.add.UsecaseAddFriendRepository
import app.meetacy.backend.usecase.integration.friends.get.UsecaseGetFriendsRepository
import app.meetacy.backend.usecase.integration.meetings.create.UsecaseCreateMeetingRepository
import app.meetacy.backend.usecase.integration.meetings.get.UsecaseGetMeetingRepository
import app.meetacy.backend.usecase.integration.meetings.list.UsecaseMeetingsListRepository
import app.meetacy.backend.usecase.integration.meetings.participate.UsecaseParticipateMeetingRepository
import app.meetacy.backend.usecase.integration.notifications.get.UsecaseGetNotificationsRepository
import app.meetacy.backend.usecase.integration.notifications.read.UsecaseReadNotificationsRepository
import app.meetacy.backend.usecase.integration.users.UsecaseUserRepository
import app.meetacy.backend.usecase.meetings.CreateMeetingUsecase
import app.meetacy.backend.usecase.meetings.GetMeetingUsecase
import app.meetacy.backend.usecase.meetings.GetMeetingsListUsecase
import app.meetacy.backend.usecase.meetings.ParticipateMeetingUsecase
import app.meetacy.backend.usecase.notification.GetNotificationsUsecase
import app.meetacy.backend.usecase.notification.ReadNotificationsUsecase
import app.meetacy.backend.usecase.users.GetUserSafeUsecase
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import org.junit.Test
import java.io.File
import org.jetbrains.exposed.sql.Database
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking


class RoutingKtTest {

    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080
    val databaseUrl = System.getenv("DATABASE_URL") ?: error("Please provide a database url")
    val databaseUser = System.getenv("DATABASE_USER") ?: ""
    val databasePassword = System.getenv("DATABASE_PASSWORD") ?: ""

    val database = Database.connect(
        databaseUrl,
        user = databaseUser,
        password = databasePassword
    )
    val db = database
    val authRepository = DatabaseAuthRepository(db)

    val client = HttpClient(CIO)

    @Test
    fun testPostFilesUpload(): Unit = runBlocking {

        startEndpoints(
            port = port,
            wait = false,
            authDependencies = AuthDependencies(
                emailDependencies = EmailDependencies(
                    linkEmailRepository = UsecaseLinkEmailRepository(
                        usecase = LinkEmailUsecase(
                            storage = DatabaseLinkEmailStorage(db),
                            mailer = DatabaseLinkEmailMailer,
                            hashGenerator = DefaultHashGenerator,
                            authRepository = authRepository
                        )
                    ),
                    confirmEmailRepository = UsecaseConfirmEmailRepository(
                        usecase = ConfirmEmailUsecase(
                            storage = DatabaseConfirmEmailStorage(db)
                        )
                    )
                ),
                tokenGenerateRepository = UsecaseTokenGenerateRepository(
                    usecase = GenerateTokenUsecase(
                        storage = DatabaseGenerateTokenStorage(DefaultHashGenerator, db),
                        tokenGenerator = DefaultHashGenerator
                    )
                )
            ),
            userRepository = UsecaseUserRepository(
                usecase = GetUserSafeUsecase(
                    authRepository = authRepository,
                    usersViewsRepository = DatabaseGetUsersViewsRepository(db)
                )
            ),
            friendsDependencies = FriendsDependencies(
                addFriendRepository = UsecaseAddFriendRepository(
                    usecase = AddFriendUsecase(
                        authRepository = authRepository,
                        getUsersViewsRepository = DatabaseGetUsersViewsRepository(db),
                        storage = DatabaseAddFriendStorage(db)
                    )
                ),
                getFriendsRepository = UsecaseGetFriendsRepository(
                    usecase = GetFriendsUsecase(
                        authRepository = authRepository,
                        getUsersViewsRepository = DatabaseGetUsersViewsRepository(db),
                        storage = DatabaseGetFriendsStorage(db)
                    )
                )
            ),
            meetingsDependencies = MeetingsDependencies(
                meetingsListRepository = UsecaseMeetingsListRepository(
                    usecase = GetMeetingsListUsecase(
                        authRepository = authRepository,
                        storage = DatabaseGetMeetingsListStorage(db),
                        getMeetingsViewsRepository = DatabaseGetMeetingsViewsRepository(db)
                    )
                ),
                createMeetingRepository = UsecaseCreateMeetingRepository(
                    usecase = CreateMeetingUsecase(
                        hashGenerator = DefaultHashGenerator,
                        storage = DatabaseCreateMeetingStorage(db),
                        authRepository = authRepository,
                        viewMeetingRepository = DatabaseCreateMeetingViewMeetingRepository(db)
                    )
                ),
                getMeetingRepository = UsecaseGetMeetingRepository(
                    usecase = GetMeetingUsecase(
                        authRepository = authRepository,
                        getMeetingsViewsRepository = DatabaseGetMeetingsViewsRepository(db)
                    )
                ),
                participateMeetingRepository = UsecaseParticipateMeetingRepository(
                    usecase = ParticipateMeetingUsecase(
                        authRepository = authRepository,
                        storage = DatabaseParticipateMeetingStorage(db),
                        getMeetingsViewsRepository = DatabaseGetMeetingsViewsRepository(db)
                    )
                ),
            ),
            notificationsDependencies = NotificationsDependencies(
                getNotificationsRepository = UsecaseGetNotificationsRepository(
                    usecase = GetNotificationsUsecase(
                        authRepository = authRepository,
                        usersRepository = DatabaseGetUsersViewsRepository(db),
                        meetingsRepository = DatabaseGetMeetingsViewsRepository(db),
                        storage = DatabaseGetNotificationStorage(db)
                    )
                ),
                readNotificationsRepository = UsecaseReadNotificationsRepository(
                    usecase = ReadNotificationsUsecase(
                        authRepository = authRepository,
                        storage = DatabaseReadNotificationsStorage(db)
                    )
                )
            )
        )

        val boundary = "WebAppBoundary"
        println(client.post("http://localhost:8080/files/upload") {
            setBody(
                MultiPartFormDataContent(
                    formData {
                        append("identity", "12:hjfksdfoi487iusdf9y8wefuihcsiuyhfew978dsfh")
                        append(
                            "image", File("D:/y9Kap/Downloads/Pictures/Володя.png").readBytes(), Headers.build {
                                append(HttpHeaders.ContentType, "image/png")
                                append(HttpHeaders.ContentDisposition, "filename=\"Во233.png\"")
                            }
                        )
                    },
                    boundary,
                    ContentType.MultiPart.FormData.withParameter("boundary", boundary)
                ),

            )

        }.bodyAsText()
        )
    }

}