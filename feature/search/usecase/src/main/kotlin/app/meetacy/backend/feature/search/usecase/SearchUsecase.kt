package app.meetacy.backend.feature.search.usecase

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.address.Address
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.auth.authorizeWithUserId
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.meetings.*
import app.meetacy.backend.types.place.Place
import app.meetacy.backend.types.search.SearchItem
import app.meetacy.backend.types.users.*
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.util.Comparator

class SearchUsecase(
    private val storage: Storage,
    private val authRepository: AuthRepository,
    private val meetingsRepository: ViewMeetingsRepository,
    private val usersRepository: ViewUsersRepository
) {
    suspend fun search(
        accessIdentity: AccessIdentity,
        location: Location,
        prompt: String,
        categoryLimit: Int = 5
    ): Result = coroutineScope {
        val viewerId = authRepository.authorizeWithUserId(accessIdentity) {
            return@coroutineScope Result.InvalidAccessIdentity
        }

        val resultsAsync = listOf(
            async {
                storage
                    .getMeetings(prompt, categoryLimit)
                    .let { meetings -> meetingsRepository.viewMeetings(viewerId, meetings) }
                    .map { meeting -> SearchItem.Meeting(meeting) }
            },
            async {
                storage
                    .getUsers(prompt, categoryLimit)
                    .let { users -> usersRepository.viewUsers(viewerId, users) }
                    .map { user -> SearchItem.User(user) }
            },
            async {
                storage
                    .getAddresses(prompt, location, categoryLimit)
                    .map { place -> SearchItem.Place(place) }
            }
        )


        val results = resultsAsync.awaitAll().flatten()
        Result.Success(results)
    }

    interface Storage {
        suspend fun getMeetings(titlePrefix: String, limit: Int): List<FullMeeting>
        suspend fun getUsers(nicknamePrefix: String, limit: Int): List<FullUser>
        suspend fun getAddresses(prompt: String, location: Location, limit: Int): List<Place>
    }

    sealed interface Result {
        data class Success(val results: List<SearchItem>) : Result
        data object InvalidAccessIdentity : Result
    }
}
