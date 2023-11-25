package app.meetacy.backend.feature.email.usecase.integration

import app.meetacy.di.builder.DIBuilder

fun DIBuilder.email() {
    confirmEmailUsecase()
    linkEmailUsecase()
}
