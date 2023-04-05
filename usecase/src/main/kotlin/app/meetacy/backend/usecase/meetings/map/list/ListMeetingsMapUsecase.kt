package app.meetacy.backend.usecase.meetings.map.list

import app.meetacy.backend.stdlib.flow.chunked
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.amount.amount
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.types.*
import kotlinx.coroutines.flow.*
import java.time.Duration
import java.time.Instant
import java.time.ZoneOffset

class ListMeetingsMapUsecase(
    private val authRepository: AuthRepository,
    private val getMeetingsViewsRepository: GetMeetingsViewsRepository,
    private val storage: Storage
) {
    suspend fun getMeetingsList(
        accessIdentity: AccessIdentity,
        chunkSize: Amount = 100.amount,
        limit: Int = 10_000
    ): Result {
        val userId = authRepository.authorizeWithUserId(accessIdentity) {
            return Result.InvalidAccessIdentity
        }

        val now = Instant.now()
            .atOffset(ZoneOffset.UTC)
            .toLocalDate()
            .minusDays(1)


        Instant.ofEpochMilli(10_000) - Duration.ofDays(30)

        val history = storage
            .getMeetingsHistoryFlow(userId)
            .chunked(chunkSize.int) { meetingIds ->
                getMeetingsViewsRepository.getMeetingsViews(userId, meetingIds)
            }
            .transform { list -> emitAll(list.asFlow()) }
            .filter { view -> view.date.javaLocalDate >= now }
            .take(limit)
            .toList()

        return Result.Success(history)
    }

    sealed interface Result {
        class Success(val meetings: List<MeetingView>) : Result
        object InvalidAccessIdentity : Result
    }

    interface Storage {
        suspend fun getMeetingsHistoryFlow(userId: UserId): Flow<MeetingId>
    }
}
