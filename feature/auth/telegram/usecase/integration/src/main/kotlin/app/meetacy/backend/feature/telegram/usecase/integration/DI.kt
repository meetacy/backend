package app.meetacy.backend.feature.telegram.usecase.integration

import app.meetacy.backend.feature.telegram.usecase.integration.await.telegramAuthAwaitUsecase
import app.meetacy.backend.feature.telegram.usecase.integration.finish.telegramAuthFinishUsecase
import app.meetacy.backend.feature.telegram.usecase.integration.middleware.telegramAuthMiddleware
import app.meetacy.backend.feature.telegram.usecase.integration.prelogin.telegramAuthPreloginUsecase
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.telegram() {
    telegramAuthAwaitUsecase()
    telegramAuthFinishUsecase()
    telegramAuthMiddleware()
    telegramAuthPreloginUsecase()
}
