@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.auth.email

import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.di.dependency.Dependency
import app.meetacy.backend.endpoint.auth.email.EmailDependencies
import app.meetacy.backend.infrastructure.integrations.auth.email.confirm.confirmEmailRepository
import app.meetacy.backend.infrastructure.integrations.auth.email.link.linkEmailRepository

val DI.emailDependencies: EmailDependencies by Dependency

fun DIBuilder.email() {
    confirmEmailRepository()
    linkEmailRepository()
    val emailDependencies by singleton<EmailDependencies> {
        EmailDependencies(
            linkEmailRepository,
            confirmEmailRepository
        )
    }
}
