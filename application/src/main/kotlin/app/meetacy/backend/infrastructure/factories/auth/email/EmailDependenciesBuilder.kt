package app.meetacy.backend.infrastructure.factories.auth.email

import app.meetacy.backend.endpoint.auth.email.EmailDependencies
import org.jetbrains.exposed.sql.Database

fun emailDependencies(
    db: Database
): EmailDependencies = EmailDependencies(
    linkEmailRepository = linkEmailRepository(db),
    confirmEmailRepository = confirmEmailRepository(db)
)
