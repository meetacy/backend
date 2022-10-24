package app.meetacy.backend.endpoint.meetings.avatar

import app.meetacy.backend.endpoint.meetings.avatar.add.AddMeetingAvatarRepository
import app.meetacy.backend.endpoint.meetings.avatar.add.addAvatar
import io.ktor.server.routing.*

class MeetingAvatarDependencies(
    val addMeetingAvatarRepository: AddMeetingAvatarRepository,
    //val deleteAvatarRepository: DeleteAvatarRepository
)

fun Route.avatar(dependencies: MeetingAvatarDependencies) = route("/avatar") {
    addAvatar(dependencies.addMeetingAvatarRepository)
    //deleteAvatar(dependencies.deleteAvatarRepository)
}