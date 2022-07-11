package app.meetacy.backend.endpoint.integration.mock

import app.meetacy.backend.endpoint.startEndpoints
import app.meetacy.backend.endpoint.integration.mock.generateToken.MockAccessHashGeneratorIntegration
import app.meetacy.backend.endpoint.integration.mock.generateToken.MockHashGeneratorIntegration
import app.meetacy.backend.endpoint.integration.mock.generateToken.MockTokenGeneratorIntegration
import app.meetacy.backend.endpoint.integration.mock.generateToken.MockUserGeneratorIntegration
import app.meetacy.backend.endpoint.integration.mock.getUser.MockUserProviderIntegration
import app.meetacy.backend.endpoint.integration.usecase.emailConfirm.ConfirmStorageUsecaseIntegration
import app.meetacy.backend.endpoint.integration.usecase.emailLink.LinkEmailRepositoryUsecaseIntegration
import app.meetacy.backend.usecase.integration.mock.mockConfirmEmailUsecase
import app.meetacy.backend.usecase.integration.mock.mockLinkEmailUsecase

fun startMockEndpoints(
    port: Int,
    wait: Boolean
) {
    startEndpoints(
        port = port,
        wait = wait,
        linkEmailRepository = LinkEmailRepositoryUsecaseIntegration(mockLinkEmailUsecase()),
        confirmStorage = ConfirmStorageUsecaseIntegration(mockConfirmEmailUsecase()),
        getUsersProvider = MockUserProviderIntegration,
        tokenGenerator = MockTokenGeneratorIntegration(
            userGenerator = MockUserGeneratorIntegration(
                MockAccessHashGeneratorIntegration
            ),
            hashGenerator = MockHashGeneratorIntegration
        )
    )
}
