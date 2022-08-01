package app.meetacy.backend.infrastructure

import app.meetacy.backend.endpoint.meetings.MeetingsDependencies
import app.meetacy.backend.endpoint.notifications.NotificationsDependencies
import app.meetacy.backend.endpoint.startEndpoints
import app.meetacy.backend.hash.integration.DefaultHashGenerator
import app.meetacy.backend.test.TestAddFriendRepository
import app.meetacy.backend.test.TestGetFriendsRepository
import app.meetacy.backend.test.TestMeetingRepository
import app.meetacy.backend.test.TestParticipateMeetingRepository
import app.meetacy.backend.mock.integration.*
import app.meetacy.backend.mock.integration.types.MockAuthRepository
import app.meetacy.backend.mock.integration.types.MockGetMeetingsViewsRepository
import app.meetacy.backend.mock.integration.types.MockGetUsersViewsRepository
import app.meetacy.backend.usecase.auth.GenerateTokenUsecase
import app.meetacy.backend.usecase.email.ConfirmEmailUsecase
import app.meetacy.backend.usecase.email.LinkEmailUsecase
import app.meetacy.backend.usecase.integration.auth.UsecaseTokenGenerateRepository
import app.meetacy.backend.usecase.integration.email.confirm.UsecaseConfirmEmailRepository
import app.meetacy.backend.usecase.integration.email.link.UsecaseLinkEmailRepository
import app.meetacy.backend.usecase.integration.meetings.create.UsecaseCreateMeetingRepository
import app.meetacy.backend.usecase.integration.meetings.list.UsecaseMeetingsListRepository
import app.meetacy.backend.usecase.integration.notifications.get.UsecaseGetNotificationsRepository
import app.meetacy.backend.usecase.integration.notifications.read.UsecaseReadNotificationsRepository
import app.meetacy.backend.usecase.integration.users.UsecaseUserRepository
import app.meetacy.backend.usecase.meetings.CreateMeetingUsecase
import app.meetacy.backend.usecase.meetings.GetMeetingsListUsecase
import app.meetacy.backend.usecase.notification.GetNotificationsUsecase
import app.meetacy.backend.usecase.notification.ReadNotificationsUsecase
import app.meetacy.backend.usecase.users.GetUserSafeUsecase

fun startMockEndpoints(
    port: Int,
    wait: Boolean
) {
    startEndpoints(
        port = port,
        wait = wait,
        linkEmailRepository = UsecaseLinkEmailRepository(
            usecase = LinkEmailUsecase(
                storage = MockLinkEmailStorage,
                mailer = MockLinkEmailMailer,
                hashGenerator = DefaultHashGenerator
            )
        ),
        confirmEmailRepository = UsecaseConfirmEmailRepository(
            usecase = ConfirmEmailUsecase(
                storage = MockConfirmEmailStorage
            )
        ),
        userRepository = UsecaseUserRepository(
            usecase = GetUserSafeUsecase(
                storage = MockGetUserSafeStorage,
                usersViewsRepository = MockGetUsersViewsRepository
            )
        ),
        tokenGenerateRepository = UsecaseTokenGenerateRepository(
            usecase = GenerateTokenUsecase(
                storage = MockGenerateTokenStorage(DefaultHashGenerator),
                tokenGenerator = DefaultHashGenerator
            )
        ),
        addFriendRepository = TestAddFriendRepository,
        getFriendsRepository = TestGetFriendsRepository,
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
                    storage = MockCreateMeetingStorage,
                    authRepository = MockAuthRepository,
                    viewMeetingRepository = MockCreateMeetingViewMeetingRepository
                )
            ),
            meetingRepository = TestMeetingRepository,
            participateMeetingRepository = TestParticipateMeetingRepository,
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
