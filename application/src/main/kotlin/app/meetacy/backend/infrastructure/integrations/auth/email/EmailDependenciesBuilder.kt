package app.meetacy.backend.infrastructure.integrations.auth.email

import app.meetacy.backend.endpoint.auth.email.EmailDependencies
import app.meetacy.backend.infrastructure.integrations.auth.email.confirm.confirmEmailRepository
import org.jetbrains.exposed.sql.Database

fun emailDependencies(
    db: Database
): EmailDependencies = EmailDependencies(
    linkEmailRepository = linkEmailRepository(db),
    confirmEmailRepository = confirmEmailRepository(db)
)
