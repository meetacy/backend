package app.meetacy.backend.feature.invitations.usecase.integration

import app.meetacy.backend.feature.invitations.usecase.integration.accept.acceptInvitation
import app.meetacy.backend.feature.invitations.usecase.integration.cancel.cancelInvitation
import app.meetacy.backend.feature.invitations.usecase.integration.create.createInvitation
import app.meetacy.backend.feature.invitations.usecase.integration.deny.denyInvitation
import app.meetacy.backend.feature.invitations.usecase.integration.view.getInvitationViews
import app.meetacy.backend.feature.invitations.usecase.integration.view.viewInvitation
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.invitations() {
    acceptInvitation()
    cancelInvitation()
    createInvitation()
    denyInvitation()
    getInvitationViews()
    viewInvitation()
}
