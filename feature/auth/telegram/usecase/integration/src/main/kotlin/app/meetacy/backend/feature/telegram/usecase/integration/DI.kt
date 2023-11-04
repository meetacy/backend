package app.meetacy.backend.feature.telegram.usecase.integration

import app.meetacy.di.builder.DIBuilder

fun DIBuilder.telegram() {
    generateTelegramTemporaryTokenUsecase()
}
