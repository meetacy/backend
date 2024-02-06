import app.meetacy.sdk.MeetacyApi
import app.meetacy.sdk.production
import app.meetacy.sdk.types.auth.Token

val token = Token("")

suspend fun main() {
    val server = MeetacyApi.production().authorized(token)

}