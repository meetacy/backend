package app.meetacy.backend.infrastructure

import app.meetacy.backend.endpoint.startEndpoints
import app.meetacy.backend.integration.mock.generateToken.MockAccessHashGeneratorIntegration
import app.meetacy.backend.integration.mock.generateToken.MockHashGeneratorIntegration
import app.meetacy.backend.integration.mock.generateToken.MockTokenGeneratorIntegration
import app.meetacy.backend.integration.mock.generateToken.MockUserGeneratorIntegration
import app.meetacy.backend.mock.integration.mockConfirmEmailUsecase
import app.meetacy.backend.mock.integration.mockGetUserUsecase
import app.meetacy.backend.usecase.integration.emailConfirm.ConfirmStorageUsecaseIntegration
import app.meetacy.backend.usecase.integration.emailLink.LinkEmailRepositoryUsecaseIntegration
import app.meetacy.backend.usecase.integration.mock.mockLinkEmailUsecase
import app.meetacy.backend.usecase.integration.users.usecaseUserProvider

fun startMockEndpoints(
    port: Int,
    wait: Boolean
) {
    startEndpoints(
        port = port,
        wait = wait,
        linkEmailRepository = LinkEmailRepositoryUsecaseIntegration(mockLinkEmailUsecase()),
        confirmStorage = ConfirmStorageUsecaseIntegration(mockConfirmEmailUsecase()),
        getUsersProvider = usecaseUserProvider(mockGetUserUsecase()),
        tokenGenerator = MockTokenGeneratorIntegration(
            userGenerator = MockUserGeneratorIntegration(
                MockAccessHashGeneratorIntegration
            ),
            hashGenerator = MockHashGeneratorIntegration
        )
    )
}
