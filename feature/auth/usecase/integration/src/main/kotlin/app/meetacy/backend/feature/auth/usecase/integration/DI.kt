package app.meetacy.backend.feature.auth.usecase.integration

import app.meetacy.di.builder.DIBuilder

fun DIBuilder.auth() {
    createTokenUsecase()
    generateTokenUsecase()
}
