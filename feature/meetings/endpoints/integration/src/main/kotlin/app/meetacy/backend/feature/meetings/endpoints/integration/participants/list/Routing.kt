package app.meetacy.backend.feature.meetings.endpoints.integration.participants.list

import app.meetacy.backend.feature.meetings.endpoints.participants.list.ListMeetingParticipantsRepository
import app.meetacy.backend.feature.meetings.endpoints.participants.list.ListParticipantsResult
import app.meetacy.backend.feature.meetings.endpoints.participants.list.listMeetingParticipants
import app.meetacy.backend.feature.meetings.usecase.participants.list.ListMeetingParticipantsUsecase
import app.meetacy.backend.feature.meetings.usecase.participants.list.ListMeetingParticipantsUsecase.Result
import app.meetacy.backend.types.paging.serializable.serializable
import app.meetacy.backend.types.paging.serializable.type
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.amount.type
import app.meetacy.backend.types.serializable.meetings.type
import app.meetacy.backend.types.serializable.users.serializable
import app.meetacy.backend.types.users.UserView
import app.meetacy.di.global.di
import io.ktor.server.routing.*

fun Route.listMeetingParticipants() {
    val listMeetingParticipantsUsecase: ListMeetingParticipantsUsecase by di.getting

    val repository = ListMeetingParticipantsRepository { params ->
        when (
            val participants = listMeetingParticipantsUsecase.getMeetingParticipants(
                accessIdentity = params.token.type(),
                meetingIdentity = params.meetingId.type(),
                amount = params.amount.type(),
                pagingId = params.pagingId?.type()
            )
        ) {
            Result.MeetingNotFound -> ListParticipantsResult.MeetingNotFound
            Result.TokenInvalid -> TODO()
            is Result.Success -> ListParticipantsResult.Success(
                paging = participants.paging
                    .mapItems(UserView::serializable)
                    .serializable()
            )
        }
    }

    listMeetingParticipants(repository)
}
