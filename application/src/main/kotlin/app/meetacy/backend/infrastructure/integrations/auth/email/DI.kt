@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.auth.email

import app.meetacy.backend.endpoint.auth.email.EmailDependencies
import app.meetacy.backend.infrastructure.integrations.auth.email.confirm.confirmEmailRepository
import app.meetacy.backend.infrastructure.integrations.auth.email.link.linkEmailRepository
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

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
