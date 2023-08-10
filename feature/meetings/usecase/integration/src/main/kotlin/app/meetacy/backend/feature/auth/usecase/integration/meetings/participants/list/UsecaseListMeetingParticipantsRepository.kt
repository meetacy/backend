package app.meetacy.backend.feature.auth.usecase.integration.meetings.participants.list

import app.meetacy.backend.endpoint.meetings.participants.list.ListMeetingParticipantsParams
import app.meetacy.backend.endpoint.meetings.participants.list.ListMeetingParticipantsRepository
import app.meetacy.backend.endpoint.meetings.participants.list.ListParticipantsResult
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.amount.type
import app.meetacy.backend.types.serializable.meeting.type
import app.meetacy.backend.types.serialization.paging.serializable
import app.meetacy.backend.usecase.integration.types.mapToEndpoint
import app.meetacy.backend.usecase.meetings.participants.list.ListMeetingParticipantsUsecase
import app.meetacy.backend.usecase.meetings.participants.list.ListMeetingParticipantsUsecase.Result
import app.meetacy.backend.usecase.types.UserView

class UsecaseListMeetingParticipantsRepository(
    val usecase: ListMeetingParticipantsUsecase
) : ListMeetingParticipantsRepository {
    override suspend fun listParticipants(
        params: ListMeetingParticipantsParams
    ): ListParticipantsResult = with (params) {
        when (
            val result = usecase.getMeetingParticipants(
                accessIdentity = token.type(),
                meetingIdentity = meetingId.type()!!,
                amount = amount.type(),
                pagingId = pagingId?.type()
            )
        ) {
            is Result.MeetingNotFound -> ListParticipantsResult.MeetingNotFound
            is Result.TokenInvalid -> ListParticipantsResult.TokenInvalid
            is Result.Success -> ListParticipantsResult.Success(
                paging = result.paging.map { users ->
                    users.map(UserView::mapToEndpoint)
                }.serializable()
            )
        }
    }
}
