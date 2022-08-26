package app.meetacy.backend.infrastructure

import app.meetacy.backend.database.integration.DatabaseCreateMeetingStorage
import app.meetacy.backend.database.integration.email.DatabaseConfirmEmailStorage
import app.meetacy.backend.database.integration.friends.DatabaseAddFriendStorage
import app.meetacy.backend.database.integration.friends.DatabaseGetFriendsStorage
import app.meetacy.backend.endpoint.auth.AuthDependencies
import app.meetacy.backend.endpoint.auth.email.EmailDependencies
import app.meetacy.backend.endpoint.friends.FriendsDependencies
import app.meetacy.backend.endpoint.meetings.MeetingsDependencies
import app.meetacy.backend.endpoint.notifications.NotificationsDependencies
import app.meetacy.backend.endpoint.startEndpoints
import app.meetacy.backend.hash.integration.DefaultHashGenerator
import app.meetacy.backend.mock.integration.*
import app.meetacy.backend.mock.integration.email.MockLinkEmailMailer
import app.meetacy.backend.mock.integration.email.MockLinkEmailStorage
import app.meetacy.backend.mock.integration.meetings.create.MockCreateMeetingViewMeetingRepository
import app.meetacy.backend.mock.integration.meetings.list.MockGetMeetingsListStorage
import app.meetacy.backend.mock.integration.meetings.participate.MockParticipateMeetingStorage
import app.meetacy.backend.mock.integration.types.MockAuthRepository
import app.meetacy.backend.mock.integration.types.MockGetMeetingsViewsRepository
import app.meetacy.backend.mock.integration.types.MockGetUsersViewsRepository
import app.meetacy.backend.mock.integration.users.MockGetUserSafeStorage
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
                        storage = MockLinkEmailStorage(db),
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
                    storage = MockGenerateTokenStorage(DefaultHashGenerator),
                    tokenGenerator = DefaultHashGenerator
                )
            )
        ),
        userRepository = UsecaseUserRepository(
            usecase = GetUserSafeUsecase(
                storage = MockGetUserSafeStorage,
                usersViewsRepository = MockGetUsersViewsRepository
            )
        ),
        friendsDependencies = FriendsDependencies(
            addFriendRepository = UsecaseAddFriendRepository(
                usecase = AddFriendUsecase(
                    authRepository = MockAuthRepository,
                    getUsersViewsRepository = MockGetUsersViewsRepository,
                    storage = DatabaseAddFriendStorage(db)
                )
            ),
            getFriendsRepository = UsecaseGetFriendsRepository(
                usecase = GetFriendsUsecase(
                    authRepository = MockAuthRepository,
                    getUsersViewsRepository = MockGetUsersViewsRepository,
                    storage = DatabaseGetFriendsStorage(db)
                )
            )
        ),
        meetingsDependencies = MeetingsDependencies(
            meetingsListRepository = UsecaseMeetingsListRepository(
                usecase = GetMeetingsListUsecase(
                    authRepository = MockAuthRepository,
                    storage = MockGetMeetingsListStorage,
                    getMeetingsViewsRepository = MockGetMeetingsViewsRepository
                )
            ),
            createMeetingRepository = UsecaseCreateMeetingRepository(
                usecase = CreateMeetingUsecase(
                    hashGenerator = DefaultHashGenerator,
                    storage = DatabaseCreateMeetingStorage(db),
                    authRepository = MockAuthRepository,
                    viewMeetingRepository = MockCreateMeetingViewMeetingRepository
                )
            ),
            getMeetingRepository = UsecaseGetMeetingRepository(
                usecase = GetMeetingUsecase(
                    authRepository = MockAuthRepository,
                    getMeetingsViewsRepository = MockGetMeetingsViewsRepository
                )
            ),
            participateMeetingRepository = UsecaseParticipateMeetingRepository(
                usecase = ParticipateMeetingUsecase(
                    authRepository = MockAuthRepository,
                    storage = MockParticipateMeetingStorage,
                    getMeetingsViewsRepository = MockGetMeetingsViewsRepository
                )
            ),
        ),
        notificationsDependencies = NotificationsDependencies(
            getNotificationsRepository = UsecaseGetNotificationsRepository(
                usecase = GetNotificationsUsecase(
                    authRepository = MockAuthRepository,
                    usersRepository = MockGetUsersViewsRepository,
                    meetingsRepository = MockGetMeetingsViewsRepository,
                    storage = MockGetNotificationStorage
                )
            ),
            readNotificationsRepository = UsecaseReadNotificationsRepository(
                usecase = ReadNotificationsUsecase(
                    authRepository = MockAuthRepository,
                    storage = MockReadNotificationsStorage
                )
            )
        )
    )
}
