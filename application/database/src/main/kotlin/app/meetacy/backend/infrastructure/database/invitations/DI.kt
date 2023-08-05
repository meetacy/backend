@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.database.invitations

import app.meetacy.backend.database.invitations.InvitationsStorage
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.database.invitations.accept.acceptInvitation
import app.meetacy.backend.infrastructure.database.invitations.cancel.cancelInvitation
import app.meetacy.backend.infrastructure.database.invitations.create.createInvitation
import app.meetacy.backend.infrastructure.database.invitations.deny.denyInvitation
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.invitationsStorage: InvitationsStorage by Dependency

fun DIBuilder.invitations() {
    acceptInvitation()
    cancelInvitation()
    createInvitation()
    denyInvitation()
    val invitationsStorage by singleton { InvitationsStorage(database) }
}
