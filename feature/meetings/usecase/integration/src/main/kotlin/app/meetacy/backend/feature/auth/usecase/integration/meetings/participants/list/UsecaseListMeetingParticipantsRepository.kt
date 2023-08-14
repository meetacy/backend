package app.meetacy.backend.feature.auth.usecase.integration.meetings.participants.list

import app.meetacy.backend.endpoint.meetings.participants.list.ListMeetingParticipantsParams
import app.meetacy.backend.endpoint.meetings.participants.list.ListMeetingParticipantsRepository
import app.meetacy.backend.endpoint.meetings.participants.list.ListParticipantsResult
import app.meetacy.backend.feature.auth.usecase.integration.types.mapToEndpoint
import app.meetacy.backend.feature.auth.usecase.meetings.participants.list.ListMeetingParticipantsUsecase
import app.meetacy.backend.feature.auth.usecase.types.UserView
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.amount.type
import app.meetacy.backend.types.serializable.meeting.type
import app.meetacy.backend.types.serialization.paging.serializable

class UsecaseListMeetingParticipantsRepository(
    val usecase: ListMeetingParticipantsUsecase
) : ListMeetingParticipantsRepository {
    override suspend fun listParticipants(
        params: ListMeetingParticipantsParams
    ): ListParticipantsResult = usecase.getMeetingParticipants(
                accessIdentity = params.token.type(),
                meetingIdentity = params.meetingId.type()!!,
                amount = params.amount.type(),
                pagingId = params.pagingId?.type()
            ).toEndpoint()

    private fun ListMeetingParticipantsUsecase.Result.toEndpoint(): ListParticipantsResult = when (this) {
        is ListMeetingParticipantsUsecase.Result.MeetingNotFound -> ListParticipantsResult.MeetingNotFound
        is ListMeetingParticipantsUsecase.Result.TokenInvalid -> ListParticipantsResult.TokenInvalid
        is ListMeetingParticipantsUsecase.Result.Success -> ListParticipantsResult.Success(
            paging = this.paging.map { users ->
                users.map(UserView::mapToEndpoint)
            }.serializable()
        )
    }
}
