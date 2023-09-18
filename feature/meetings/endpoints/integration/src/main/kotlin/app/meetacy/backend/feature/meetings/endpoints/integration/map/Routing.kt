package app.meetacy.backend.feature.meetings.endpoints.integration.map

import app.meetacy.backend.feature.meetings.endpoints.integration.map.list.listMeetingsMap
import app.meetacy.di.DI
import io.ktor.server.routing.*

internal fun Route.meetingsMap(di: DI) = route("/map") {
    listMeetingsMap(di)
}
