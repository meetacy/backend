package app.meetacy.backend.feature.updates.usecase.integration

import app.meetacy.backend.feature.updates.usecase.integration.middleware.updatesMiddleware
import app.meetacy.backend.feature.updates.usecase.integration.stream.streamUpdatesUsecase
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.updates() {
    updatesMiddleware()
    streamUpdatesUsecase()
}
