package app.meetacy.backend.feature.invitations.database.integration

import app.meetacy.backend.feature.invitations.database.invitations.InvitationsStorage
import app.meetacy.di.builder.DIBuilder
import org.jetbrains.exposed.sql.Database

fun DIBuilder.invitationStorage() {
    val invitationsStorage by singleton<InvitationsStorage> {
        val database: Database by getting
        InvitationsStorage(database)
    }
}
