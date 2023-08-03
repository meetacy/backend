@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.database.invitations

import app.meetacy.backend.database.invitations.InvitationsStorage
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.invitationsStorage: InvitationsStorage by Dependency

fun DIBuilder.invitations() {
    val invitationsStorage by singleton { InvitationsStorage(database) }
}
