package app.meetacy.backend.feature.search.usecase.integration

import app.meetacy.backend.feature.meetings.database.meetings.MeetingsStorage
import app.meetacy.backend.feature.search.usecase.SearchUsecase
import app.meetacy.backend.feature.users.database.users.UsersStorage
import app.meetacy.backend.types.address.Address
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.meetings.ViewMeetingsRepository
import app.meetacy.backend.types.place.Place
import app.meetacy.backend.types.users.FullUser
import app.meetacy.backend.types.users.ViewUsersRepository
import app.meetacy.di.builder.DIBuilder
import app.meetacy.google.maps.GooglePlace
import app.meetacy.google.maps.GooglePlacesTextSearch

internal fun DIBuilder.searchUsecase() {
    val searchUsecase by singleton {
        val viewMeetingsRepository: ViewMeetingsRepository by getting
        val viewUsersRepository: ViewUsersRepository by getting
        val authRepository: AuthRepository by getting

        val meetingsStorage: MeetingsStorage by getting
        val usersStorage: UsersStorage by getting
        val googlePlacesTextSearch: GooglePlacesTextSearch by getting

        val storage = object : SearchUsecase.Storage {
            override suspend fun getMeetings(
                titlePrefix: String,
                limit: Int
            ) = meetingsStorage.searchMeetings(titlePrefix, limit)

            override suspend fun getUsers(
                nicknamePrefix: String,
                limit: Int
            ): List<FullUser> = usersStorage.searchUsers(nicknamePrefix, limit)

            override suspend fun getAddresses(
                prompt: String,
                location: Location,
                limit: Int
            ): List<Place> = runCatching {
                googlePlacesTextSearch.search(GooglePlace.Location(location.latitude, location.longitude), prompt)
            }.getOrElse { emptyList() }
                .take(limit)
                .map { googlePlace ->
                    Place(
                        address = with (googlePlace.address) {
                            Address(country, city, street, placeName)
                        },
                        location = with (googlePlace.location) {
                            Location(latitude, longitude)
                        }
                    )
                }
        }

        SearchUsecase(
            storage = storage,
            authRepository = authRepository,
            meetingsRepository = viewMeetingsRepository,
            usersRepository = viewUsersRepository
        )
    }
}
