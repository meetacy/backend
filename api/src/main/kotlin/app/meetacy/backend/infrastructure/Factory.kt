package app.meetacy.backend.infrastructure

import app.meetacy.backend.endpoint.startEndpoints
import app.meetacy.backend.integration.mock.generateToken.MockAccessHashGeneratorIntegration
import app.meetacy.backend.integration.mock.generateToken.MockHashGeneratorIntegration
import app.meetacy.backend.integration.mock.generateToken.MockTokenGeneratorIntegration
import app.meetacy.backend.integration.mock.generateToken.MockUserGeneratorIntegration
import app.meetacy.backend.integration.test.TestAddFriendRepository
import app.meetacy.backend.integration.test.TestCreateMeetingRepository
import app.meetacy.backend.integration.test.TestGetFriendsRepository
import app.meetacy.backend.integration.test.TestMeetingProvider
import app.meetacy.backend.integration.test.TestMeetingsProvider
import app.meetacy.backend.integration.test.TestParticipateMeetingRepository
import app.meetacy.backend.mock.integration.mockConfirmEmailUsecase
import app.meetacy.backend.mock.integration.mockGetUserUsecase
import app.meetacy.backend.usecase.integration.emailConfirm.usecaseConfirmEmailRepository
import app.meetacy.backend.usecase.integration.emailLink.usecaseLinkEmailRepository
import app.meetacy.backend.usecase.integration.mock.mockLinkEmailUsecase
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
        tokenGenerator = MockTokenGeneratorIntegration(
            userGenerator = MockUserGeneratorIntegration(
                MockAccessHashGeneratorIntegration
            ),
            hashGenerator = MockHashGeneratorIntegration
        ),
        meetingsProvider = TestMeetingsProvider,
        createMeetingRepository = TestCreateMeetingRepository,
        meetingProvider = TestMeetingProvider,
        participateMeetingRepository = TestParticipateMeetingRepository,
        addFriendRepository = TestAddFriendRepository,
        getFriendsRepository = TestGetFriendsRepository
    )
}
