package app.meetacy.backend.infrastructure.database.email.link

import app.meetacy.backend.database.integration.email.DatabaseLinkEmailMailer
import app.meetacy.backend.database.integration.email.DatabaseLinkEmailStorage
import app.meetacy.backend.infrastructure.database.auth.authRepository
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.usecase.email.LinkEmailUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.linkEmailUsecase: LinkEmailUsecase by Dependency

fun DIBuilder.linkEmail() {
    val linkEmailUsecase by singleton<LinkEmailUsecase> {
        LinkEmailUsecase(
            storage = DatabaseLinkEmailStorage(database),
            mailer = DatabaseLinkEmailMailer,
            hashGenerator = get(),
            authRepository = authRepository
        )
    }
}
