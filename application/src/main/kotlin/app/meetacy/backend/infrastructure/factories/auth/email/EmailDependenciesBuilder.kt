package app.meetacy.backend.infrastructure.factories.auth.email

import app.meetacy.backend.database.integration.email.DatabaseConfirmEmailStorage
import app.meetacy.backend.database.integration.email.DatabaseLinkEmailMailer
import app.meetacy.backend.database.integration.email.DatabaseLinkEmailStorage
import app.meetacy.backend.endpoint.auth.email.EmailDependencies
import app.meetacy.backend.hash.integration.DefaultHashGenerator
import app.meetacy.backend.infrastructure.factories.auth.authRepository
import app.meetacy.backend.usecase.email.ConfirmEmailUsecase
import app.meetacy.backend.usecase.email.LinkEmailUsecase
import app.meetacy.backend.usecase.integration.email.confirm.UsecaseConfirmEmailRepository
import app.meetacy.backend.usecase.integration.email.link.UsecaseLinkEmailRepository
import app.meetacy.backend.usecase.types.AuthRepository
import org.jetbrains.exposed.sql.Database

fun emailDependencies(
    db: Database,
    authRepository: AuthRepository = authRepository(db)
): EmailDependencies = EmailDependencies(
    linkEmailRepository = UsecaseLinkEmailRepository(
        usecase = LinkEmailUsecase(
            storage = DatabaseLinkEmailStorage(db),
            mailer = DatabaseLinkEmailMailer,
            hashGenerator = DefaultHashGenerator,
            authRepository = authRepository
        )
    ),
    confirmEmailRepository = UsecaseConfirmEmailRepository(
        usecase = ConfirmEmailUsecase(
            storage = DatabaseConfirmEmailStorage(db)
        )
    )
)
