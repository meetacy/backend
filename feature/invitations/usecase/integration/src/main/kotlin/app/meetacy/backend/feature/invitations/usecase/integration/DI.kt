package app.meetacy.backend.feature.invitations.usecase.integration

import app.meetacy.di.builder.DIBuilder

fun DIBuilder.invitations() {
    acceptInvitation()
    cancelInvitation()
    createInvitation()
    denyInvitation()
    getInvitationViews()
    viewInvitation()
}
