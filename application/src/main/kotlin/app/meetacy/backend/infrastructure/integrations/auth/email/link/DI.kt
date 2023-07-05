@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.auth.email.link

import app.meetacy.backend.database.integration.email.DatabaseLinkEmailMailer
import app.meetacy.backend.database.integration.email.DatabaseLinkEmailStorage
import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.di.dependency.Dependency
import app.meetacy.backend.endpoint.auth.email.link.LinkEmailRepository
import app.meetacy.backend.hash.integration.DefaultHashGenerator
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.integrations.auth.authRepository
import app.meetacy.backend.usecase.email.LinkEmailUsecase
import app.meetacy.backend.usecase.integration.email.link.UsecaseLinkEmailRepository

val DI.linkEmailRepository: LinkEmailRepository by Dependency

fun DIBuilder.linkEmailRepository() {
    val linkEmailRepository by singleton<LinkEmailRepository> {
        UsecaseLinkEmailRepository(
            usecase = LinkEmailUsecase(
                storage = DatabaseLinkEmailStorage(database),
                mailer = DatabaseLinkEmailMailer,
                hashGenerator = DefaultHashGenerator,
                authRepository = authRepository
            )
        )
    }
}
