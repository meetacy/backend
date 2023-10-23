package app.meetacy.backend.feature.search.usecase

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.address.Address
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.auth.authorizeWithUserId
import app.meetacy.backend.types.meetings.GetMeetingsViewsRepository
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.meetings.getMeetingsViews
import app.meetacy.backend.types.search.SearchResult
import app.meetacy.backend.types.users.GetUsersViewsRepository
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.types.users.getUsersViews
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class SearchUsecase(
    private val storage: Storage,
    private val authRepository: AuthRepository,
    private val meetingsRepository: GetMeetingsViewsRepository,
    private val usersRepository: GetUsersViewsRepository
) {
    suspend fun search(
        accessIdentity: AccessIdentity,
        prompt: String
    ): Result = coroutineScope {
        val viewerId = authRepository.authorizeWithUserId(accessIdentity) {
            return@coroutineScope Result.InvalidAccessIdentity
        }

        val resultsAsync = listOf(
            async {
                storage
                    .getMeetings(prompt)
                    .let { meetingIds -> meetingsRepository.getMeetingsViews(viewerId, meetingIds) }
                    .map { meeting -> SearchResult.Meeting(meeting) }
            },
            async {
                storage
                    .getUsers(prompt)
                    .let { userIds -> usersRepository.getUsersViews(viewerId, userIds) }
                    .map { user -> SearchResult.User(user) }
            },
            async {
                storage
                    .getAddresses(prompt)
                    .map { address -> SearchResult.Place(address) }
            }
        )

        val results = resultsAsync.awaitAll().flatten()
        Result.Success(results)
    }

    interface Storage {
        suspend fun getMeetings(titlePrefix: String): List<MeetingId>
        suspend fun getUsers(nicknamePrefix: String): List<UserId>
        suspend fun getAddresses(prompt: String): List<Address>
    }

    sealed interface Result {
        data class Success(val results: List<SearchResult>) : Result
        data object InvalidAccessIdentity : Result
    }
}
