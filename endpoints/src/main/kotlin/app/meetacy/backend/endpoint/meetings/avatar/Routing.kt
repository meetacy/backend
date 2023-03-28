package app.meetacy.backend.endpoint.meetings.avatar

import app.meetacy.backend.endpoint.meetings.avatar.add.AddMeetingAvatarRepository
import app.meetacy.backend.endpoint.meetings.avatar.add.addAvatar
import app.meetacy.backend.endpoint.meetings.avatar.delete.DeleteMeetingAvatarRepository
import app.meetacy.backend.endpoint.meetings.avatar.delete.deleteAvatar
import io.ktor.server.routing.*

class MeetingAvatarDependencies(
    val addMeetingAvatarRepository: AddMeetingAvatarRepository,
    val deleteMeetingAvatarRepository: DeleteMeetingAvatarRepository
)

fun Route.avatar(dependencies: MeetingAvatarDependencies) = route("/avatar") {
    addAvatar(dependencies.addMeetingAvatarRepository)
    deleteAvatar(dependencies.deleteMeetingAvatarRepository)
}
