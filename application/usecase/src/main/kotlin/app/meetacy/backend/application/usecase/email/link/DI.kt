package app.meetacy.backend.application.usecase.email.link

import app.meetacy.backend.feature.email.endpoints.link.LinkEmailRepository
import app.meetacy.backend.infrastructure.database.email.link.linkEmailMailer
import app.meetacy.backend.infrastructure.database.email.link.linkEmailStorage
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
                storage = linkEmailStorage,
                mailer = linkEmailMailer,
                hashGenerator = get(),
                authRepository = get()
            )
        )
    }
}
