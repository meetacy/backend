@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.database.invitations

import app.meetacy.backend.database.invitations.InvitationsStorage
import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.di.dependency.Dependency
import app.meetacy.backend.infrastructure.database.database

val DI.invitationsStorage: InvitationsStorage by Dependency

fun DIBuilder.invitations() {
    val invitationsStorage by singleton { InvitationsStorage(database) }
}
