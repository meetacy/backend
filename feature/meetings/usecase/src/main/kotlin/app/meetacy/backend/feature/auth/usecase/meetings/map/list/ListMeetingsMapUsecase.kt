package app.meetacy.backend.feature.auth.usecase.meetings.map.list

import app.meetacy.backend.stdlib.flow.chunked
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.amount.amount
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.meters.kilometers
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.types.*
import kotlinx.coroutines.flow.*
import java.time.Instant
import java.time.ZoneOffset

class ListMeetingsMapUsecase(
    private val authRepository: app.meetacy.backend.feature.auth.usecase.types.AuthRepository,
    private val getMeetingsViewsRepository: GetMeetingsViewsRepository,
    private val viewMeetingsRepository: ViewMeetingsRepository,
    private val storage: Storage
) {
    suspend fun getMeetingsList(
        accessIdentity: AccessIdentity,
        location: Location,
        chunkSize: Amount = 100.amount,
        participatingMeetingsLimit: Amount = 10_000.amount,
        publicMeetingsLimit: Amount = 100.amount
    ): Result {
        val userId = authRepository.authorizeWithUserId(accessIdentity) {
            return Result.InvalidAccessIdentity
        }

        val now = Instant.now()
            .atOffset(ZoneOffset.UTC)
            .toLocalDate()
            .minusDays(1)

        val history = storage
            .getMeetingsHistoryFlow(userId)
            .chunked(chunkSize.int) { meetingIds ->
                getMeetingsViewsRepository.getMeetingsViews(userId, meetingIds)
            }
            .transform { list -> emitAll(list.asFlow()) }
            .filter { view -> view.date.javaLocalDate >= now }
            .take(participatingMeetingsLimit.int)
            .toList()


        val public = storage.getPublicMeetingsFlow()
            .filter { meeting ->
                meeting.location.measureDistance(location) <= 50.kilometers
            }
            .filter { meeting -> meeting.date.javaLocalDate >= now }
            .chunked(chunkSize.int) { meetings ->
                viewMeetingsRepository.viewMeetings(
                    userId,
                    meetings)
            }
            .transform { list -> emitAll(list.asFlow()) }
            .take(publicMeetingsLimit.int)
            .toList()

        return Result.Success(meetings = (history + public).distinctBy(MeetingView::id))
    }

    sealed interface Result {
        class Success(val meetings: List<MeetingView>) : Result
        object InvalidAccessIdentity : Result
    }

    interface Storage {
        suspend fun getMeetingsHistoryFlow(userId: UserId): Flow<MeetingId>
        suspend fun getPublicMeetingsFlow(): Flow<FullMeeting>
    }
}
