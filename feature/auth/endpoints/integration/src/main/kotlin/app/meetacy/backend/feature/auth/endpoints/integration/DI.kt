package app.meetacy.backend.feature.auth.endpoints.integration

import app.meetacy.backend.feature.auth.endpoints.integration.generate.tokenGenerator
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.auth() {
    tokenGenerator()
}
