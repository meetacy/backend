package app.meetacy.backend.integration.mock

import app.meetacy.backend.endpoint.startEndpoints
import app.meetacy.backend.integration.mock.emailConfirm.MockConfirmStorageIntegration
import app.meetacy.backend.integration.mock.emailLink.MockConfirmHashGeneratorIntegration
import app.meetacy.backend.integration.mock.emailLink.MockLinkEmailStorageIntegration
import app.meetacy.backend.integration.mock.emailLink.MockMailerIntegration
import app.meetacy.backend.integration.mock.emailLink.MockTextProviderIntegration

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
        confirmStorage = MockConfirmStorageIntegration
    )
}
