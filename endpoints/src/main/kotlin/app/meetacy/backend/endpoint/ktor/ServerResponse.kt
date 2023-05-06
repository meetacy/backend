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
    // Last errorCode: 17
    companion object {
        val InvalidToken = Failure(false, 1, "Please provide a valid token")
        val InvalidMeetingIdentity = Failure(false, 2, "Please provide a valid meetingId")
        val InvalidFileIdentity = Failure(false, 3, "Please provide a valid fileId")
        val InvalidLink = Failure(false, 4, "This link is invalid. Please consider to create a new one")
        val LastNotificationIdInvalid = Failure(false, 5, "Please provide a valid notificationId")

        val InvalidNickname = Failure(false, 6, "Please provide a valid nickname")
        val InvalidTitleOrDescription = Failure(false, 7, "Please provide a valid title or description")

        val FriendNotFound = Failure(false, 8, "Friend was not found")
        val UserNotFound = Failure(false, 9, "FullUser not found")

        val MeetingAlreadyParticipate = Failure(false, 10, "You are already participating in this meeting")
        val NullEditParams = Failure(false, 14, "Specify at least one edit parameter")
        val FriendAlreadyAdded = Failure(false, 11, "Friend already added")

        val ExpiredLink = Failure(false, 12, "This link was expired. Please consider to create a new one")
        val LinkMaxAttemptsReached = Failure(false, 13, "You have reached max attempts for today. Please try again later.")

        val ApiVersionIsNotSpecified = Failure(false, 14, "Please specify api version using header 'Api-Version'")

        val UnableToInvite = Failure(false, 16, "You are unable to invite this user. Probably, you are not admin of event, or this person is not your subscriber or friend")
        val FriendAlreadyInvited = Failure(false, 17, "You have already invited this friend. Try another one")

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
