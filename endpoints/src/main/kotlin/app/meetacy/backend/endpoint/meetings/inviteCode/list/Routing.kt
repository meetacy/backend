package app.meetacy.backend.endpoint.meetings.inviteCode.list

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.meeting.inviteCode.MeetingInviteCode
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.serialization.access.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.amount.AmountSerializable
import app.meetacy.backend.types.serialization.meeting.MeetingIdentitySerializable
import app.meetacy.backend.types.serialization.paging.PagingIdSerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class GetMeetingInviteCodesParams(
    val token: AccessIdentitySerializable,
    val meetingIdentity: MeetingIdentitySerializable,
    val amount: AmountSerializable,
    val pagingId: PagingIdSerializable?
)

interface GetMeetingInviteCodesRepository {
    suspend fun getMeetingInviteCodes(params: GetMeetingInviteCodesParams): GetMeetingInviteCodesResult
}

sealed interface GetMeetingInviteCodesResult {
    class Success(val inviteCodes: List<MeetingInviteCode>) : GetMeetingInviteCodesResult
    object InvalidAccessIdentity : GetMeetingInviteCodesResult
    object InvalidMeetingIdentity : GetMeetingInviteCodesResult
    object CannotAccess : GetMeetingInviteCodesResult
}


fun Route.getMeetingInviteCodes(repository: GetMeetingInviteCodesRepository) = post("/list") {
    val params = call.receive<GetMeetingInviteCodesParams>()

    when (val result = repository.getMeetingInviteCodes(params)) {
        is GetMeetingInviteCodesResult.Success -> {
            val rawInviteCodes = result.inviteCodes.map { it.string }
            call.respondSuccess(rawInviteCodes)
        }
        is GetMeetingInviteCodesResult.InvalidAccessIdentity -> call.respondFailure(Failure.InvalidAccessIdentity)
        is GetMeetingInviteCodesResult.InvalidMeetingIdentity -> call.respondFailure(Failure.InvalidMeetingIdentity)
        is GetMeetingInviteCodesResult.CannotAccess -> call.respondFailure(Failure.InvalidNickname) // todo CannotAccess???
    }
}
