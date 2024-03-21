package app.meetacy.backend.feature.meetings.usecase.map.list

import app.meetacy.backend.stdlib.flow.chunked
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.amount.amount
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.auth.authorizeWithUserId
import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.datetime.meetacyDate
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.meetings.*
import app.meetacy.backend.types.meters.kilometers
import app.meetacy.backend.types.users.UserId
import kotlinx.coroutines.flow.*
import java.time.Instant
import java.time.ZoneOffset

class ListMeetingsMapUsecase(
    private val authRepository: AuthRepository,
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

        val history = storage.getMeetingsHistoryFlowAscending(
            userId = userId,
            amount = participatingMeetingsLimit,
            afterDate = now.minusDays(1).meetacyDate
        ).let { meetingIds ->
            getMeetingsViewsRepository.getMeetingsViews(userId, meetingIds)
        }

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
        data class Success(val meetings: List<MeetingView>) : Result
        data object InvalidAccessIdentity : Result
    }

    interface Storage {
        suspend fun getMeetingsHistoryFlowAscending(
            userId: UserId,
            amount: Amount,
            afterDate: Date
        ): List<MeetingId>
        suspend fun getPublicMeetingsFlow(): Flow<FullMeeting>
    }
}
