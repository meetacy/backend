package app.meetacy.backend.integration.mock

import app.meetacy.backend.endpoint.startEndpoints
import app.meetacy.backend.integration.mock.emailConfirm.MockConfirmStorageIntegration
import app.meetacy.backend.integration.mock.emailLink.MockConfirmHashGeneratorIntegration
import app.meetacy.backend.integration.mock.emailLink.MockLinkEmailStorageIntegration
import app.meetacy.backend.integration.mock.emailLink.MockMailerIntegration
import app.meetacy.backend.integration.mock.emailLink.MockTextProviderIntegration
import app.meetacy.backend.integration.mock.generateToken.MockAccessHashGeneratorIntegration
import app.meetacy.backend.integration.mock.generateToken.MockHashGeneratorIntegration
import app.meetacy.backend.integration.mock.generateToken.MockTokenGeneratorIntegration
import app.meetacy.backend.integration.mock.generateToken.MockUserGeneratorIntegration
import app.meetacy.backend.integration.mock.getUser.MockUserProviderIntegration

fun startMockEndpoints(
    port: Int,
    wait: Boolean,
    onEmailReceived: (String, String) -> Unit = { _, _ -> }
) {
    startEndpoints(
        port = port,
        wait = wait,
        mailer = MockMailerIntegration(onEmailReceived, MockTextProviderIntegration),
        linkEmailStorage = MockLinkEmailStorageIntegration(MockConfirmHashGeneratorIntegration),
        confirmStorage = MockConfirmStorageIntegration,
        getUsersProvider = MockUserProviderIntegration,
        tokenGenerator = MockTokenGeneratorIntegration(
            userGenerator = MockUserGeneratorIntegration(
                MockAccessHashGeneratorIntegration
            ),
            hashGenerator = MockHashGeneratorIntegration
        )
    )
}
