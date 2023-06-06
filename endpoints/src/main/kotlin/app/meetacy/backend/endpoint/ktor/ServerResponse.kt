@file:Suppress("FunctionName")

package app.meetacy.backend.endpoint.ktor

import kotlinx.serialization.Serializable

@Serializable
data class Success<out T>(
    val status: Boolean,
    val result: T
)

@Serializable
data class Failure(
    val status: Boolean,
    val errorCode: Int,
    val errorMessage: String
) {
    // next errorCode -- 17
    companion object {
        val InvalidToken = Failure(false, 1, "Please provide a valid token")
        val InvalidMeetingIdentity = Failure(false, 2, "Please provide a valid meetingId")
        val InvalidFileIdentity = Failure(false, 3, "Please provide a valid fileId")
        val InvalidLink = Failure(false, 4, "This link is invalid. Please consider to create a new one")
        val LastNotificationIdInvalid = Failure(false, 5, "Please provide a valid notificationId")

        val UsernameAlreadyOccupied = Failure(false, 6, "This username is not a unique username")

        val FriendNotFound = Failure(false, 7, "Friend was not found")
        val UserNotFound = Failure(false, 8, "FullUser not found")
        val MeetingAlreadyParticipate = Failure(false, 9, "You are already participating in this meeting")
        val NullEditParams = Failure(false, 10, "Specify at least one edit parameter")
        val FriendAlreadyAdded = Failure(false, 11, "Friend already added")

        val ExpiredLink = Failure(false, 12, "This link was expired. Please consider to create a new one")
        val LinkMaxAttemptsReached = Failure(false, 13, "You have reached max attempts for today. Please try again later.")

        val ApiVersionIsNotSpecified = Failure(false, 14, "Please specify api version using header 'Api-Version'")
        val ValidationError = Failure(false, 15, "")
        val InvalidUtf8String = Failure(false, 16, "Please provide valid string")

        fun UnhandledException(throwable: Throwable): Failure = Failure(
            status = false,
            errorCode = 15,
            errorMessage = buildString {
                appendLine("Unhandled exception occurred. Stacktrace: ")
                append(throwable.stackTraceToString())
            }
        )
    }
}

@Serializable
data class EmptySuccess(
    val status: Boolean
)
