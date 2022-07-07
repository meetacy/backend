package app.meetacy.backend.integration.mockEndpoint

import app.meetacy.backend.endpoint.startEndpoints

fun startMockEndpoints(port: Int, wait: Boolean) =
    startEndpoints(port, wait, MockAuthProvider, MockUsersProvider)
