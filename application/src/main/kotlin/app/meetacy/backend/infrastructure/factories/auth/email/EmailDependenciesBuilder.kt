package app.meetacy.backend.infrastructure.factories.auth.email

import app.meetacy.backend.database.integration.email.DatabaseConfirmEmailStorage
import app.meetacy.backend.endpoint.auth.email.EmailDependencies
import app.meetacy.backend.usecase.email.ConfirmEmailUsecase
import app.meetacy.backend.usecase.integration.email.confirm.UsecaseConfirmEmailRepository
import org.jetbrains.exposed.sql.Database

fun emailDependencies(
    db: Database
): EmailDependencies = EmailDependencies(
    linkEmailRepository = linkEmailRepository(db),
    confirmEmailRepository = UsecaseConfirmEmailRepository(
        usecase = ConfirmEmailUsecase(
            storage = DatabaseConfirmEmailStorage(db)
        )
    )
)
