package app.meetacy.backend.endpoint.ktor

import kotlinx.serialization.Serializable

@Serializable
data class Success<out T>(
    val status: Boolean,
    val result: T
)

@Serializable
data class Failure(
    val status: Boolean = false,
    val errorCode: Int,
    val errorMessage: String
)

@Serializable
data class EmptySuccess(
    val status: Boolean
)

class ResponseCode(
    errorCode: Int,
    errorMessage: String
) {
    val customFailure = Failure(errorCode = errorCode, errorMessage = errorMessage)

    companion object {
        val InvalidAccessIdentity = Failure(errorCode = 1, errorMessage = "Please provide a valid accessIdentity")
        val InvalidMeetingIdentity = Failure(errorCode = 2, errorMessage = "Please provide a valid meetingIdentity")
        val InvalidFileIdentity = Failure(errorCode = 3, errorMessage = "Please provide a valid fileIdentity")
        val InvalidLink = Failure(errorCode = 4, errorMessage = "This link is invalid. Please consider to create a new one")
        val LastNotificationIdInvalid = Failure(errorCode = 5, errorMessage = "Please provide a valid notification id")

        val InvalidNickname = Failure(errorCode = 6, errorMessage = "Please provide a valid nickname")
        val InvalidTitleOrDescription = Failure(errorCode = 7, errorMessage = "Please provide a valid title or description")

        val FriendNotFound = Failure(errorCode = 8, errorMessage = "Friend was not found")
        val UserNotFound = Failure(errorCode = 9, errorMessage = "FullUser not found")

        val MeetingAlreadyParticipate = Failure(errorCode = 10, errorMessage = "You are already participating in this meeting")
        val FriendAlreadyAdded = Failure(errorCode = 11, errorMessage = "Friend already added")

        val ExpiredLink = Failure(errorCode = 12, errorMessage = "This link was expired. Please consider to create a new one")
        val LinkMaxAttemptsReached = Failure(errorCode = 13, errorMessage = "You have reached max attempts for today. Please try again later.")
    }
}
