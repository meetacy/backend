@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.auth.email.link

import app.meetacy.backend.endpoint.auth.email.link.LinkEmailRepository
import app.meetacy.backend.infrastructure.database.email.link.linkEmailUsecase
import app.meetacy.backend.usecase.integration.email.link.UsecaseLinkEmailRepository
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.linkEmailRepository: LinkEmailRepository by Dependency

fun DIBuilder.linkEmailRepository() {
    val linkEmailRepository by singleton<LinkEmailRepository> {
        UsecaseLinkEmailRepository(
            usecase = linkEmailUsecase
        )
    }
}
