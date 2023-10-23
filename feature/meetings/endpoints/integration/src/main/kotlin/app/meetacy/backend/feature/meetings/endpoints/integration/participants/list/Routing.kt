package app.meetacy.backend.feature.meetings.endpoints.integration.participants.list

import app.meetacy.backend.feature.meetings.endpoints.participants.list.ListMeetingParticipantsRepository
import app.meetacy.backend.feature.meetings.endpoints.participants.list.ListParticipantsResult
import app.meetacy.backend.feature.meetings.endpoints.participants.list.listMeetingParticipants
import app.meetacy.backend.feature.meetings.usecase.participants.list.ListMeetingParticipantsUsecase
import app.meetacy.backend.types.paging.serializable.serializable
import app.meetacy.backend.types.paging.serializable.type
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.amount.type
import app.meetacy.backend.types.serializable.meetings.type
import app.meetacy.backend.types.serializable.users.serializable
import app.meetacy.backend.types.users.UserView
import app.meetacy.di.DI
import io.ktor.server.routing.*

internal fun Route.listMeetingParticipants(di: DI) {
    val listMeetingParticipantsUsecase: ListMeetingParticipantsUsecase by di.getting

    val repository =
        ListMeetingParticipantsRepository { token, meetingId, amount, pagingId ->
            when (
                val result = listMeetingParticipantsUsecase.getMeetingParticipants(
                    accessIdentity = token.type(),
                    amount = amount.type(),
                    meetingIdentity = meetingId.type(),
                    pagingId = pagingId?.type()
                )
            ) {
                ListMeetingParticipantsUsecase.Result.MeetingNotFound -> ListParticipantsResult.MeetingNotFound
                is ListMeetingParticipantsUsecase.Result.Success -> ListParticipantsResult.Success(paging = result.paging
                    .mapItems(UserView::serializable)
                    .serializable())
                ListMeetingParticipantsUsecase.Result.TokenInvalid -> ListParticipantsResult.TokenInvalid
            }
        }
    listMeetingParticipants(repository)
}
