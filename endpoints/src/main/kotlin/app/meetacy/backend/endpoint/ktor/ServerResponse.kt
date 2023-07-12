@file:Suppress("FunctionName")

package app.meetacy.backend.endpoint.ktor

import io.rsocket.kotlin.payload.buildPayload
import io.rsocket.kotlin.payload.data
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

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
    
    constructor(errorCode: Int, errorMessage: String) : this(status = false, errorCode, errorMessage)
    
    // next errorCode -- 26
    // errorCode 14 is occupied for a fileSizeLimit, because his is inline
    // errorCode 15 is occupied for a UnhandledException

    companion object {
        val InvalidToken = Failure(1, "Please provide a valid token")
        val InvalidMeetingIdentity = Failure(2, "Please provide a valid meetingId")
        val InvalidFileIdentity = Failure(3, "Please provide a valid fileId")
        val InvalidLink = Failure(4, "This link is invalid. Please consider to create a new one")
        val LastNotificationIdInvalid = Failure(5, "Please provide a valid notificationId")

        val UsernameAlreadyOccupied = Failure(6, "This username is not a unique username")
        val FriendNotFound = Failure(7, "Friend was not found")
        val UserNotFound = Failure(8, "FullUser not found")
        val MeetingAlreadyParticipate = Failure(9, "You are already participating in this meeting")
        val NullEditParams = Failure(10, "Specify at least one edit parameter")
        val FriendAlreadyAdded = Failure(11, "Friend already added")

        val ExpiredLink = Failure(12, "This link was expired. Please consider to create a new one")
        val LinkMaxAttemptsReached = Failure(13, "You have reached max attempts for today. Please try again later.")

        val ApiVersionIsNotSpecified = Failure(25, "Please specify api version using header 'Api-Version'")
        val InvalidUtf8String = Failure(16, "Please provide valid string")

        val UnableToInvite = Failure(17, "You are unable to invite this user. Probably, you are not admin of event, or this person is not your subscriber or friend")
        val UserAlreadyInvited = Failure(18, "You have already invited this friend. Try another one")
        val InvitationNotFound = Failure(22, "Invitation you are requested for is not found ¬_¬")

        fun UnhandledException(throwable: Throwable): Failure {
            System.err.println(throwable.stackTraceToString())
            return Failure(
                status = false,
                errorCode = 15,
                errorMessage = buildString {
                    appendLine("Unhandled exception occurred. Stacktrace: ")
                    append(throwable.stackTraceToString())
                }
            )
        }
    }

    fun encodeToPayload() = buildPayload {
        data(Json.encodeToString(this@Failure))
    }
}

@Serializable
data class EmptySuccess(
    val status: Boolean
) {
    constructor() : this(status = true)

    fun encodeToPayload() = buildPayload {
        data(Json.encodeToString(this@EmptySuccess))
    }
}
