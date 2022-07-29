package app.meetacy.backend.infrastructure

import app.meetacy.backend.endpoint.meetings.MeetingsDependencies
import app.meetacy.backend.endpoint.notifications.NotificationsDependencies
import app.meetacy.backend.endpoint.notifications.get.GetNotificationsRepository
import app.meetacy.backend.endpoint.notifications.read.ReadNotificationsRepository
import app.meetacy.backend.endpoint.startEndpoints
import app.meetacy.backend.integration.test.TestAddFriendRepository
import app.meetacy.backend.integration.test.TestCreateMeetingRepository
import app.meetacy.backend.integration.test.TestGetFriendsRepository
import app.meetacy.backend.integration.test.TestMeetingProvider
import app.meetacy.backend.integration.test.TestMeetingsProvider
import app.meetacy.backend.integration.test.TestParticipateMeetingRepository
import app.meetacy.backend.mock.integration.mockConfirmEmailUsecase
import app.meetacy.backend.mock.integration.mockGenerateTokenUsecase
import app.meetacy.backend.mock.integration.mockGetUserUsecase
import app.meetacy.backend.usecase.integration.emailConfirm.usecaseConfirmEmailRepository
import app.meetacy.backend.usecase.integration.emailLink.usecaseLinkEmailRepository
import app.meetacy.backend.mock.integration.mockLinkEmailUsecase
import app.meetacy.backend.usecase.integration.auth.usecaseTokenGenerator
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
            createMeetingRepository = TestCreateMeetingRepository,
            meetingProvider = TestMeetingProvider,
            participateMeetingRepository = TestParticipateMeetingRepository,
        ),
        notificationsDependencies = NotificationsDependencies(
            getNotificationsRepository = GetNotificationsRepository,
            readNotificationsRepository = ReadNotificationsRepository
        )
    )
}
