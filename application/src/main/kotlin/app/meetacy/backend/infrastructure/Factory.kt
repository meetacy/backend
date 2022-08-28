package app.meetacy.backend.infrastructure

import app.meetacy.backend.database.integration.email.DatabaseConfirmEmailStorage
import app.meetacy.backend.database.integration.friends.DatabaseAddFriendStorage
import app.meetacy.backend.database.integration.friends.DatabaseGetFriendsStorage
import app.meetacy.backend.database.integration.meetings.DatabaseCreateMeetingStorage
import app.meetacy.backend.database.integration.meetings.DatabaseCreateMeetingViewMeetingRepository
import app.meetacy.backend.database.integration.meetings.list.DatabaseGetMeetingsListStorage
import app.meetacy.backend.database.integration.meetings.participate.DatabaseParticipateMeetingStorage
import app.meetacy.backend.database.integration.tokenGenerator.DatabaseGenerateTokenStorage
import app.meetacy.backend.database.integration.types.DatabaseAuthRepository
import app.meetacy.backend.database.integration.types.DatabaseGetMeetingsViewsRepository
import app.meetacy.backend.database.integration.types.DatabaseGetUsersViewsRepository
import app.meetacy.backend.database.integration.users.DatabaseGetUserSafeStorage
import app.meetacy.backend.endpoint.auth.AuthDependencies
import app.meetacy.backend.endpoint.auth.email.EmailDependencies
import app.meetacy.backend.endpoint.friends.FriendsDependencies
import app.meetacy.backend.endpoint.meetings.MeetingsDependencies
import app.meetacy.backend.endpoint.notifications.NotificationsDependencies
import app.meetacy.backend.endpoint.startEndpoints
import app.meetacy.backend.hash.integration.DefaultHashGenerator
import app.meetacy.backend.mock.integration.notifications.MockGetNotificationStorage
import app.meetacy.backend.mock.integration.MockReadNotificationsStorage
import app.meetacy.backend.mock.integration.email.DatabaseLinkEmailStorage
import app.meetacy.backend.mock.integration.email.MockLinkEmailMailer
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
import org.jetbrains.exposed.sql.Database

fun startMockEndpoints(
    port: Int,
    db: Database,
    wait: Boolean
) {
    startEndpoints(
        port = port,
        wait = wait,
        authDependencies = AuthDependencies(
            emailDependencies = EmailDependencies(
                linkEmailRepository = UsecaseLinkEmailRepository(
                    usecase = LinkEmailUsecase(
                        storage = DatabaseLinkEmailStorage(db),
                        mailer = MockLinkEmailMailer,
                        hashGenerator = DefaultHashGenerator
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
                storage = DatabaseGetUserSafeStorage(db),
                usersViewsRepository = DatabaseGetUsersViewsRepository(db)
            )
        ),
        friendsDependencies = FriendsDependencies(
            addFriendRepository = UsecaseAddFriendRepository(
                usecase = AddFriendUsecase(
                    authRepository = DatabaseAuthRepository(db),
                    getUsersViewsRepository = DatabaseGetUsersViewsRepository(db),
                    storage = DatabaseAddFriendStorage(db)
                )
            ),
            getFriendsRepository = UsecaseGetFriendsRepository(
                usecase = GetFriendsUsecase(
                    authRepository = DatabaseAuthRepository(db),
                    getUsersViewsRepository = DatabaseGetUsersViewsRepository(db),
                    storage = DatabaseGetFriendsStorage(db)
                )
            )
        ),
        meetingsDependencies = MeetingsDependencies(
            meetingsListRepository = UsecaseMeetingsListRepository(
                usecase = GetMeetingsListUsecase(
                    authRepository = DatabaseAuthRepository(db),
                    storage = DatabaseGetMeetingsListStorage(db),
                    getMeetingsViewsRepository = DatabaseGetMeetingsViewsRepository(db)
                )
            ),
            createMeetingRepository = UsecaseCreateMeetingRepository(
                usecase = CreateMeetingUsecase(
                    hashGenerator = DefaultHashGenerator,
                    storage = DatabaseCreateMeetingStorage(db),
                    authRepository = DatabaseAuthRepository(db),
                    viewMeetingRepository = DatabaseCreateMeetingViewMeetingRepository(db)
                )
            ),
            getMeetingRepository = UsecaseGetMeetingRepository(
                usecase = GetMeetingUsecase(
                    authRepository = DatabaseAuthRepository(db),
                    getMeetingsViewsRepository = DatabaseGetMeetingsViewsRepository(db)
                )
            ),
            participateMeetingRepository = UsecaseParticipateMeetingRepository(
                usecase = ParticipateMeetingUsecase(
                    authRepository = DatabaseAuthRepository(db),
                    storage = DatabaseParticipateMeetingStorage(db),
                    getMeetingsViewsRepository = DatabaseGetMeetingsViewsRepository(db)
                )
            ),
        ),
        notificationsDependencies = NotificationsDependencies(
            getNotificationsRepository = UsecaseGetNotificationsRepository(
                usecase = GetNotificationsUsecase(
                    authRepository = DatabaseAuthRepository(db),
                    usersRepository = DatabaseGetUsersViewsRepository(db),
                    meetingsRepository = DatabaseGetMeetingsViewsRepository(db),
                    storage = MockGetNotificationStorage
                )
            ),
            readNotificationsRepository = UsecaseReadNotificationsRepository(
                usecase = ReadNotificationsUsecase(
                    authRepository = DatabaseAuthRepository(db),
                    storage = MockReadNotificationsStorage
                )
            )
        )
    )
}
