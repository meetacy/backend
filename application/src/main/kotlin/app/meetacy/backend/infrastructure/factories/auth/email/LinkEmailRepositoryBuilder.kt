package app.meetacy.backend.infrastructure.factories.auth.email

import app.meetacy.backend.database.integration.email.DatabaseLinkEmailMailer
import app.meetacy.backend.database.integration.email.DatabaseLinkEmailStorage
import app.meetacy.backend.endpoint.auth.email.link.LinkEmailRepository
import app.meetacy.backend.hash.integration.DefaultHashGenerator
import app.meetacy.backend.infrastructure.factories.auth.authRepository
import app.meetacy.backend.usecase.email.LinkEmailUsecase
import app.meetacy.backend.usecase.integration.email.link.UsecaseLinkEmailRepository
import app.meetacy.backend.usecase.types.AuthRepository
import org.jetbrains.exposed.sql.Database


fun linkEmailRepository(
    db: Database,
    authRepository: AuthRepository = authRepository(db)
): LinkEmailRepository = UsecaseLinkEmailRepository(
    usecase = LinkEmailUsecase(
        storage = DatabaseLinkEmailStorage(db),
        mailer = DatabaseLinkEmailMailer,
        hashGenerator = DefaultHashGenerator,
        authRepository = authRepository
    )
)
