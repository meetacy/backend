//import app.meetacy.backend.database.integration.email.DatabaseConfirmEmailStorage
//import app.meetacy.backend.database.integration.email.DatabaseLinkEmailMailer
//import app.meetacy.backend.database.integration.email.DatabaseLinkEmailStorage
//import app.meetacy.backend.database.integration.files.DatabaseGetFileRepository
//import app.meetacy.backend.database.integration.files.DatabaseUploadFileStorage
//import app.meetacy.backend.database.integration.friends.DatabaseAddFriendStorage
//import app.meetacy.backend.database.integration.friends.DatabaseGetFriendsStorage
//import app.meetacy.backend.database.integration.meetings.create.DatabaseCreateMeetingStorage
//import app.meetacy.backend.database.integration.meetings.create.DatabaseCreateMeetingViewMeetingRepository
//import app.meetacy.backend.database.integration.meetings.list.DatabaseGetMeetingsListStorage
//import app.meetacy.backend.database.integration.meetings.participate.DatabaseParticipateMeetingStorage
//import app.meetacy.backend.database.integration.notifications.DatabaseGetNotificationStorage
//import app.meetacy.backend.database.integration.notifications.DatabaseReadNotificationsStorage
//import app.meetacy.backend.database.integration.tokenGenerator.DatabaseGenerateTokenStorage
//import app.meetacy.backend.database.integration.types.DatabaseAuthRepository
//import app.meetacy.backend.database.integration.types.DatabaseGetMeetingsViewsRepository
//import app.meetacy.backend.database.integration.types.DatabaseGetUsersViewsRepository
//import app.meetacy.backend.endpoint.auth.AuthDependencies
//import app.meetacy.backend.endpoint.auth.email.EmailDependencies
//import app.meetacy.backend.endpoint.files.FilesDependencies
//import app.meetacy.backend.endpoint.friends.FriendsDependencies
//import app.meetacy.backend.endpoint.meetings.MeetingsDependencies
//import app.meetacy.backend.endpoint.notifications.NotificationsDependencies
//import app.meetacy.backend.endpoint.startEndpoints
//import app.meetacy.backend.hash.integration.DefaultHashGenerator
//import app.meetacy.backend.usecase.auth.GenerateTokenUsecase
//import app.meetacy.backend.usecase.email.ConfirmEmailUsecase
//import app.meetacy.backend.usecase.email.LinkEmailUsecase
//import app.meetacy.backend.usecase.files.upload.UploadFileUsecase
//import app.meetacy.backend.usecase.friends.add.AddFriendUsecase
//import app.meetacy.backend.usecase.friends.get.GetFriendsUsecase
//import app.meetacy.backend.usecase.integration.auth.UsecaseTokenGenerateRepository
//import app.meetacy.backend.usecase.integration.email.confirm.UsecaseConfirmEmailRepository
//import app.meetacy.backend.usecase.integration.email.link.UsecaseLinkEmailRepository
//import app.meetacy.backend.usecase.integration.files.UsecaseUploadFileRepository
//import app.meetacy.backend.usecase.integration.friends.add.UsecaseAddFriendRepository
//import app.meetacy.backend.usecase.integration.friends.get.UsecaseGetFriendsRepository
//import app.meetacy.backend.usecase.integration.meetings.create.UsecaseCreateMeetingRepository
//import app.meetacy.backend.usecase.integration.meetings.get.UsecaseGetMeetingRepository
//import app.meetacy.backend.usecase.integration.meetings.list.UsecaseMeetingsListRepository
//import app.meetacy.backend.usecase.integration.meetings.participate.UsecaseParticipateMeetingRepository
//import app.meetacy.backend.usecase.integration.notifications.get.UsecaseGetNotificationsRepository
//import app.meetacy.backend.usecase.integration.notifications.read.UsecaseReadNotificationsRepository
//import app.meetacy.backend.usecase.integration.users.UsecaseUserRepository
//import app.meetacy.backend.usecase.meetings.CreateMeetingUsecase
//import app.meetacy.backend.usecase.meetings.GetMeetingUsecase
//import app.meetacy.backend.usecase.meetings.GetMeetingsListUsecase
//import app.meetacy.backend.usecase.meetings.ParticipateMeetingUsecase
//import app.meetacy.backend.usecase.notification.GetNotificationsUsecase
//import app.meetacy.backend.usecase.notification.ReadNotificationsUsecase
//import app.meetacy.backend.usecase.users.GetUserSafeUsecase
//import io.ktor.client.*
//import io.ktor.client.engine.cio.*
//import io.ktor.client.request.*
//import io.ktor.client.request.forms.*
//import io.ktor.http.*
//import org.junit.Test
//import java.io.File
//import org.jetbrains.exposed.sql.Database
//import io.ktor.client.statement.*
//import kotlinx.coroutines.runBlocking
//
//
//class RoutingKtTest {
//
//    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080
//    val databaseUrl = System.getenv("DATABASE_URL") ?: error("Please provide a database url")
//    val databaseUser = System.getenv("DATABASE_USER") ?: ""
//    val databasePassword = System.getenv("DATABASE_PASSWORD") ?: ""
//    val filesBasePath = System.getenv("FILES_BASE_PATH") ?: "files"
//
//    val database = Database.connect(
//        databaseUrl,
//        user = databaseUser,
//        password = databasePassword
//    )
//    val db = database
//    val authRepository = DatabaseAuthRepository(db)
//
//    val client = HttpClient(CIO)
//
//    @Test
//    fun testPostFilesUpload(): Unit = runBlocking {
//
//        startEndpoints(
//            port = port,
//            wait = false,
//            authDependencies = AuthDependencies(
//                emailDependencies = EmailDependencies(
//                    linkEmailRepository = UsecaseLinkEmailRepository(
//                        usecase = LinkEmailUsecase(
//                            storage = DatabaseLinkEmailStorage(db),
//                            mailer = DatabaseLinkEmailMailer,
//                            hashGenerator = DefaultHashGenerator,
//                            authRepository = authRepository
//                        )
//                    ),
//                    confirmEmailRepository = UsecaseConfirmEmailRepository(
//                        usecase = ConfirmEmailUsecase(
//                            storage = DatabaseConfirmEmailStorage(db)
//                        )
//                    )
//                ),
//                tokenGenerateRepository = UsecaseTokenGenerateRepository(
//                    usecase = GenerateTokenUsecase(
//                        storage = DatabaseGenerateTokenStorage(DefaultHashGenerator, db),
//                        tokenGenerator = DefaultHashGenerator
//                    )
//                )
//            ),
//            userRepository = UsecaseUserRepository(
//                usecase = GetUserSafeUsecase(
//                    authRepository = authRepository,
//                    usersViewsRepository = DatabaseGetUsersViewsRepository(db)
//                )
//            ),
//            friendsDependencies = FriendsDependencies(
//                addFriendRepository = UsecaseAddFriendRepository(
//                    usecase = AddFriendUsecase(
//                        authRepository = authRepository,
//                        getUsersViewsRepository = DatabaseGetUsersViewsRepository(db),
//                        storage = DatabaseAddFriendStorage(db)
//                    )
//                ),
//                getFriendsRepository = UsecaseGetFriendsRepository(
//                    usecase = GetFriendsUsecase(
//                        authRepository = authRepository,
//                        getUsersViewsRepository = DatabaseGetUsersViewsRepository(db),
//                        storage = DatabaseGetFriendsStorage(db)
//                    )
//                )
//            ),
//            meetingsDependencies = MeetingsDependencies(
//                meetingsListRepository = UsecaseMeetingsListRepository(
//                    usecase = GetMeetingsListUsecase(
//                        authRepository = authRepository,
//                        storage = DatabaseGetMeetingsListStorage(db),
//                        getMeetingsViewsRepository = DatabaseGetMeetingsViewsRepository(db)
//                    )
//                ),
//                createMeetingRepository = UsecaseCreateMeetingRepository(
//                    usecase = CreateMeetingUsecase(
//                        hashGenerator = DefaultHashGenerator,
//                        storage = DatabaseCreateMeetingStorage(db),
//                        authRepository = authRepository,
//                        viewMeetingRepository = DatabaseCreateMeetingViewMeetingRepository(db)
//                    )
//                ),
//                getMeetingRepository = UsecaseGetMeetingRepository(
//                    usecase = GetMeetingUsecase(
//                        authRepository = authRepository,
//                        getMeetingsViewsRepository = DatabaseGetMeetingsViewsRepository(db)
//                    )
//                ),
//                participateMeetingRepository = UsecaseParticipateMeetingRepository(
//                    usecase = ParticipateMeetingUsecase(
//                        authRepository = authRepository,
//                        storage = DatabaseParticipateMeetingStorage(db),
//                        getMeetingsViewsRepository = DatabaseGetMeetingsViewsRepository(db)
//                    )
//                ),
//            ),
//            notificationsDependencies = NotificationsDependencies(
//                getNotificationsRepository = UsecaseGetNotificationsRepository(
//                    usecase = GetNotificationsUsecase(
//                        authRepository = authRepository,
//                        usersRepository = DatabaseGetUsersViewsRepository(db),
//                        meetingsRepository = DatabaseGetMeetingsViewsRepository(db),
//                        storage = DatabaseGetNotificationStorage(db)
//                    )
//                ),
//                readNotificationsRepository = UsecaseReadNotificationsRepository(
//                    usecase = ReadNotificationsUsecase(
//                        authRepository = authRepository,
//                        storage = DatabaseReadNotificationsStorage(db)
//                    )
//                )
//            ),
//            filesDependencies = FilesDependencies(
//                saveFileRepository = UsecaseUploadFileRepository(
//                    usecase = UploadFileUsecase(
//                        authRepository = authRepository,
//                        storage = DatabaseUploadFileStorage(db),
//                        hashGenerator = DefaultHashGenerator
//                    ),
//                    basePath = filesBasePath
//                ),
//                getFileRepository = DatabaseGetFileRepository(
//                    database = db,
//                    basePath = filesBasePath
//                )
//            )
//        )
//
//        val boundary = "WebAppBoundary"
//        println(client.post("http://localhost:8080/files/upload") {
//            setBody(
//                MultiPartFormDataContent(
//                    formData {
//                        append("accessIdentity", "1:v2T6BWW5x4fhrZv9uyvRHtqTZwKgGl1fPvadjxDh5tHWtTPOdC2Yh2L8W2vTF6CucRdsAhYJf0C0mu3Gldkth5p0HCsKxs6N4PyChEk9FxQSP5rj3Xn6ONdFQDleSnp3S8jY3HG8Cp8b09DAwDYiOtIv5MSlbap1tNTKfamWFzkmr7H3bkFgnuMwuEqLQ8qidjt2M7iWb9hazk901UgYnaiEZk3E1zQyP7baql6cQFCv5uDj1bn85CDKjmqnDa8K")
//                        append(
//                            "image", File("D:/y9Kap/Downloads/Pictures/Володя.png").readBytes(), Headers.build {
//                                append(HttpHeaders.ContentType, "image/png")
//                                append(HttpHeaders.ContentDisposition, "filename=\"qpp.png\"")
//                            }
//                        )
//                    },
//                    boundary,
//                    ContentType.MultiPart.FormData.withParameter("boundary", boundary)
//                ),
//
//            )
//
//        }.bodyAsText()
//        )
//    }
//}