package app.meetacy.backend.infrastructure.database.email.link

import app.meetacy.backend.database.integration.email.DatabaseLinkEmailMailer
import app.meetacy.backend.database.integration.email.DatabaseLinkEmailStorage
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.usecase.email.LinkEmailUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.linkEmailStorage: LinkEmailUsecase.Storage by Dependency
val DI.linkEmailMailer: DatabaseLinkEmailMailer by Dependency

fun DIBuilder.linkEmail() {
    val linkEmailUsecase by singleton<LinkEmailUsecase.Storage> {
        DatabaseLinkEmailStorage(database)
    }
    val linkEmailMailer by singleton<DatabaseLinkEmailMailer> {
        DatabaseLinkEmailMailer
    }
}
