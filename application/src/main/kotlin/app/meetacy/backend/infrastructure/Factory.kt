package app.meetacy.backend.infrastructure

import app.meetacy.backend.database.integration.email.DatabaseConfirmEmailStorage
import app.meetacy.backend.database.integration.email.DatabaseLinkEmailMailer
import app.meetacy.backend.database.integration.email.DatabaseLinkEmailStorage
import app.meetacy.backend.database.integration.files.DatabaseGetFileRepository
import app.meetacy.backend.database.integration.files.DatabaseUploadFileStorage
import app.meetacy.backend.database.integration.friends.add.DatabaseAddFriendStorage
import app.meetacy.backend.database.integration.friends.delete.DatabaseDeleteFriendStorage
import app.meetacy.backend.database.integration.friends.get.DatabaseGetFriendsStorage
import app.meetacy.backend.database.integration.meetings.avatar.add.DatabaseAddMeetingAvatarStorage
import app.meetacy.backend.database.integration.meetings.avatar.delete.DatabaseDeleteMeetingAvatarStorage
import app.meetacy.backend.database.integration.meetings.create.DatabaseCreateMeetingStorage
import app.meetacy.backend.database.integration.meetings.create.DatabaseCreateMeetingViewMeetingRepository
import app.meetacy.backend.database.integration.meetings.delete.DatabaseDeleteMeetingStorage
import app.meetacy.backend.database.integration.meetings.get.DatabaseGetMeetingsViewsViewMeetingsRepository
import app.meetacy.backend.database.integration.meetings.history.list.DatabaseListMeetingsHistoryListStorage
import app.meetacy.backend.database.integration.meetings.map.list.DatabaseListMeetingsMapListStorage
import app.meetacy.backend.database.integration.meetings.participate.DatabaseParticipateMeetingStorage
import app.meetacy.backend.database.integration.notifications.DatabaseGetNotificationStorage
import app.meetacy.backend.database.integration.notifications.DatabaseReadNotificationsStorage
import app.meetacy.backend.database.integration.tokenGenerator.DatabaseGenerateTokenStorage
import app.meetacy.backend.database.integration.types.DatabaseAuthRepository
import app.meetacy.backend.database.integration.types.DatabaseFilesRepository
import app.meetacy.backend.database.integration.types.DatabaseGetMeetingsViewsRepository
import app.meetacy.backend.database.integration.types.DatabaseGetUsersViewsRepository
import app.meetacy.backend.database.integration.users.add.DatabaseAddUserAvatarStorage
import app.meetacy.backend.database.integration.users.delete.DatabaseDeleteUserAvatarStorage
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
import org.jetbrains.exposed.sql.Database

fun startEndpoints(
    filesBasePath: String,
    filesLimit: Long,
    port: Int,
    db: Database,
    wait: Boolean,
) {
    val authRepository = DatabaseAuthRepository(db)
    val filesRepository = DatabaseFilesRepository(db)

    val getMeetingsViewsRepository = DatabaseGetMeetingsViewsRepository(db)
    val viewMeetingsRepository = DatabaseGetMeetingsViewsViewMeetingsRepository(db)

    startEndpoints(
        port = port,
        wait = wait,
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
                    tokenGenerator = DefaultHashGenerator,
                    utf8Checker = DefaultUtf8Checker
                )
            )
        ),
        usersDependencies = UsersDependencies(
            getUserRepository = UsecaseUserRepository(
                usecase = GetUserSafeUsecase(
                    authRepository = authRepository,
                    usersViewsRepository = DatabaseGetUsersViewsRepository(db)
                )
            ),
            addUserAvatarDependencies = UserAvatarDependencies(
                addUserAvatarRepository = UsecaseAddUserAvatarRepository(
                    usecase = AddUserAvatarUsecase(
                        authRepository = authRepository,
                        filesRepository = filesRepository,
                        storage = DatabaseAddUserAvatarStorage(db)
                    )
                ),
                deleteUserAvatarRepository = UsecaseDeleteUserAvatarRepository(
                    usecase = DeleteUserAvatarUsecase(
                        authRepository = authRepository,
                        storage = DatabaseDeleteUserAvatarStorage(db)
                    )
                )
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
            listFriendsRepository = UsecaseListFriendsRepository(
                usecase = ListFriendsUsecase(
                    authRepository = authRepository,
                    getUsersViewsRepository = DatabaseGetUsersViewsRepository(db),
                    storage = DatabaseGetFriendsStorage(db)
                )
            ),
            deleteFriendRepository = UsecaseDeleteFriendRepository(
                usecase = DeleteFriendUsecase(
                    authRepository = authRepository,
                    getUsersViewsRepository = DatabaseGetUsersViewsRepository(db),
                    storage = DatabaseDeleteFriendStorage(db)
                )
            )
        ),
        meetingsDependencies = MeetingsDependencies(
            meetingsHistoryDependencies = MeetingsHistoryDependencies(
                listMeetingsHistoryRepository = UsecaseListMeetingsHistoryRepository(
                    usecase = ListMeetingsHistoryUsecase(
                        authRepository = authRepository,
                        storage = DatabaseListMeetingsHistoryListStorage(db),
                        getMeetingsViewsRepository = getMeetingsViewsRepository
                    )
                )
            ),
            meetingsMapDependencies = MeetingsMapDependencies(
                listMeetingsMapRepository = UsecaseListMeetingsMapRepository(
                    usecase = ListMeetingsMapUsecase(
                        authRepository = authRepository,
                        storage = DatabaseListMeetingsMapListStorage(db),
                        getMeetingsViewsRepository = getMeetingsViewsRepository,
                        viewMeetingsRepository = viewMeetingsRepository
                    )
                )
            ),
            createMeetingRepository = UsecaseCreateMeetingRepository(
                usecase = CreateMeetingUsecase(
                    hashGenerator = DefaultHashGenerator,
                    storage = DatabaseCreateMeetingStorage(db),
                    authRepository = authRepository,
                    viewMeetingRepository = DatabaseCreateMeetingViewMeetingRepository(db),
                    utf8Checker = DefaultUtf8Checker
                )
            ),
            getMeetingRepository = UsecaseGetMeetingRepository(
                usecase = GetMeetingUsecase(
                    authRepository = authRepository,
                    getMeetingsViewsRepository = getMeetingsViewsRepository
                )
            ),
            participateMeetingRepository = UsecaseParticipateMeetingRepository(
                usecase = ParticipateMeetingUsecase(
                    authRepository = authRepository,
                    storage = DatabaseParticipateMeetingStorage(db),
                    getMeetingsViewsRepository = getMeetingsViewsRepository
                )
            ),
            addMeetingAvatarDependencies = MeetingAvatarDependencies(
                addMeetingAvatarRepository = UsecaseAddMeetingAvatarRepository(
                    usecase = AddMeetingAvatarUsecase(
                        authRepository = authRepository,
                        filesRepository = filesRepository,
                        storage = DatabaseAddMeetingAvatarStorage(db),
                        getMeetingsViewsRepository = getMeetingsViewsRepository
                    )
                ),
                deleteMeetingAvatarRepository = UsecaseDeleteMeetingAvatarRepository(
                    usecase = DeleteMeetingAvatarUsecase(
                        authRepository = authRepository,
                        storage = DatabaseDeleteMeetingAvatarStorage(db),
                        getMeetingsViewsRepository = getMeetingsViewsRepository
                    )
                )
            ),
            deleteMeetingRepository = UsecaseDeleteMeetingRepository(
                usecase = DeleteMeetingUsecase(
                    authRepository = authRepository,
                    getMeetingsViewsRepository = getMeetingsViewsRepository,
                    storage = DatabaseDeleteMeetingStorage(db)
                )
            )
        ),
        notificationsDependencies = NotificationsDependencies(
            getNotificationsRepository = UsecaseGetNotificationsRepository(
                usecase = GetNotificationsUsecase(
                    authRepository = authRepository,
                    usersRepository = DatabaseGetUsersViewsRepository(db),
                    meetingsRepository = getMeetingsViewsRepository,
                    storage = DatabaseGetNotificationStorage(db)
                )
            ),
            readNotificationsRepository = UsecaseReadNotificationsRepository(
                usecase = ReadNotificationsUsecase(
                    authRepository = authRepository,
                    storage = DatabaseReadNotificationsStorage(db)
                )
            )
        ),
        filesDependencies = FilesDependencies(
            saveFileRepository = UsecaseUploadFileRepository(
                usecase = UploadFileUsecase(
                    authRepository = authRepository,
                    storage = DatabaseUploadFileStorage(db),
                    hashGenerator = DefaultHashGenerator
                ),
                basePath = filesBasePath,
                filesLimit = filesLimit
            ),
            getFileRepository = DatabaseGetFileRepository(
                database = db,
                basePath = filesBasePath
            )
        )
    )
}
