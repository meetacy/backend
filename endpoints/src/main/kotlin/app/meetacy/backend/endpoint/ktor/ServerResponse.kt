package app.meetacy.backend.endpoint.ktor

import kotlinx.serialization.Serializable

@Serializable
data class Success<out T>(
    val status: Boolean,
    val result: T
)

@Serializable
class Failure(
    val status: Boolean,
    val errorCode: Int,
    val errorMessage: String
) {
    companion object {
        val InvalidAccessIdentity = Failure(false, 1, "Please provide a valid token")
        val InvalidMeetingIdentity = Failure(false, 2, "Please provide a valid meetingId")
        val InvalidFileIdentity = Failure(false, 3, "Please provide a valid fileId")
        val InvalidLink = Failure(false, 4, "This link is invalid. Please consider to create a new one")
        val LastNotificationIdInvalid = Failure(false, 5, "Please provide a valid notificationId")

        val InvalidNickname = Failure(false, 6, "Please provide a valid nickname")
        val InvalidUsername = Failure(false, 7, "Please provide a valid username")
        val InvalidTitleOrDescription = Failure(false, 8, "Please provide a valid title or description")

        val FriendNotFound = Failure(false, 9, "Friend was not found")
        val UserNotFound = Failure(false, 10, "FullUser not found")

        val MeetingAlreadyParticipate = Failure(false, 11, "You are already participating in this meeting")
        val NullEditParams = Failure(false, 12, "Specify at least one edit parameter")
        val FriendAlreadyAdded = Failure(false, 13, "Friend already added")

        val ExpiredLink = Failure(false, 14, "This link was expired. Please consider to create a new one")
        val LinkMaxAttemptsReached = Failure(false, 15, "You have reached max attempts for today. Please try again later.")

        val ApiVersionIsNotSpecified = Failure(false, 16, "Please specify api version using header 'Api-Version'")
    }
}

@Serializable
data class EmptySuccess(
    val status: Boolean
)
