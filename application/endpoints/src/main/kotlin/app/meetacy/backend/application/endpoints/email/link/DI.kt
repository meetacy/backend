package app.meetacy.backend.application.endpoints.email.link

import app.meetacy.backend.feature.email.endpoints.link.LinkEmailRepository
import app.meetacy.backend.feature.email.usecase.LinkEmailUsecase
import app.meetacy.backend.feature.email.usecase.integration.link.UsecaseLinkEmailRepository
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.linkEmailRepository: LinkEmailRepository by Dependency

fun DIBuilder.linkEmailRepository() {
    val linkEmailRepository by singleton<LinkEmailRepository> {
        UsecaseLinkEmailRepository(
            usecase = LinkEmailUsecase(
                storage = get(),
                mailer = get(),
                hashGenerator = get(),
                authRepository = get()
            )
        )
    }
}
