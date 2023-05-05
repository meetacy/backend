import app.meetacy.backend.endpoint.invitations.create.CreateInvitationRepository
import app.meetacy.backend.endpoint.invitations.create.InvitationCreatingFormSerializable
import app.meetacy.backend.endpoint.invitations.create.InvitationsCreateResponse

class TestInvitations: CreateInvitationRepository {
    override suspend fun createInvitation(invitationCreatingForm: InvitationCreatingFormSerializable): InvitationsCreateResponse {
        TODO("Not yet implemented")
    }
}