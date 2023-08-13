package app.meetacy.backend.feature.meetings.usecase.integration.participants.list

import app.meetacy.backend.feature.meetings.endpoints.participants.list.ListMeetingParticipantsParams
import app.meetacy.backend.feature.meetings.endpoints.participants.list.ListMeetingParticipantsRepository
import app.meetacy.backend.feature.meetings.endpoints.participants.list.ListParticipantsResult
import app.meetacy.backend.types.paging.serializable.serializable
import app.meetacy.backend.types.paging.serializable.type
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.amount.type
import app.meetacy.backend.types.serializable.meetings.type
import app.meetacy.backend.types.serializable.users.serializable
import app.meetacy.backend.types.users.UserView
import app.meetacy.backend.feature.meetings.usecase.participants.list.ListMeetingParticipantsUsecase
import app.meetacy.backend.feature.meetings.usecase.participants.list.ListMeetingParticipantsUsecase.Result

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
                    users.map(UserView::serializable)
                }.serializable()
            )
        }
    }
}
