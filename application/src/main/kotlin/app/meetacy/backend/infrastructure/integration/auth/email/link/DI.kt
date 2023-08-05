package app.meetacy.backend.infrastructure.integration.auth.email.link

import app.meetacy.backend.database.integration.email.DatabaseLinkEmailMailer
import app.meetacy.backend.endpoint.auth.email.link.LinkEmailRepository
import app.meetacy.backend.infrastructure.database.auth.authRepository
import app.meetacy.backend.infrastructure.database.email.link.linkEmailStorage
import app.meetacy.backend.usecase.email.LinkEmailUsecase
import app.meetacy.backend.usecase.integration.email.link.UsecaseLinkEmailRepository
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.linkEmailRepository: LinkEmailRepository by Dependency

fun DIBuilder.linkEmailRepository() {
    val linkEmailRepository by singleton<LinkEmailRepository> {
        UsecaseLinkEmailRepository(
            usecase = LinkEmailUsecase(
                storage = linkEmailStorage,
                mailer = DatabaseLinkEmailMailer,
                hashGenerator = get(),
                authRepository = authRepository
            )
        )
    }
}
