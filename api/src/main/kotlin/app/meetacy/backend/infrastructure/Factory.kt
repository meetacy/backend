package app.meetacy.backend.infrastructure

import app.meetacy.backend.endpoint.meetings.MeetingsDependencies
import app.meetacy.backend.endpoint.notifications.NotificationsDependencies
import app.meetacy.backend.endpoint.startEndpoints
import app.meetacy.backend.integration.test.*
import app.meetacy.backend.mock.integration.*
import app.meetacy.backend.usecase.integration.auth.usecaseTokenGenerator
import app.meetacy.backend.usecase.integration.emailConfirm.usecaseConfirmEmailRepository
import app.meetacy.backend.usecase.integration.emailLink.usecaseLinkEmailRepository
import app.meetacy.backend.usecase.integration.meetings.usecaseCreateMeetingRepository
import app.meetacy.backend.usecase.integration.notificationsGet.usecaseGetNotificationsRepository
import app.meetacy.backend.usecase.integration.notificationsRead.usecaseReadNotificationsRepository
import app.meetacy.backend.usecase.integration.users.usecaseUserProvider

fun startMockEndpoints(
    port: Int,
    wait: Boolean
) {
    startEndpoints(
        port = port,
        wait = wait,
        linkEmailRepository = usecaseLinkEmailRepository(mockLinkEmailUsecase()),
        confirmEmailRepository = usecaseConfirmEmailRepository(mockConfirmEmailUsecase()),
        userProvider = usecaseUserProvider(mockGetUserUsecase()),
        tokenGenerator = usecaseTokenGenerator(mockGenerateTokenUsecase()),
        addFriendRepository = TestAddFriendRepository,
        getFriendsRepository = TestGetFriendsRepository,
        meetingsDependencies = MeetingsDependencies(
            meetingsProvider = TestMeetingsProvider,
            createMeetingRepository = usecaseCreateMeetingRepository(mockCreateMeetingUsecase()),
            meetingProvider = TestMeetingProvider,
            participateMeetingRepository = TestParticipateMeetingRepository,
        ),
        notificationsDependencies = NotificationsDependencies(
            getNotificationsRepository = usecaseGetNotificationsRepository(mockGetNotificationsUsecase()),
            readNotificationsRepository = usecaseReadNotificationsRepository(mockReadNotificationsUsecase())
        )
    )
}
