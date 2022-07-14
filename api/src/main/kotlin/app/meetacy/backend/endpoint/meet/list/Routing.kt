package app.meetacy.backend.endpoint.meet.list

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class ListParam(
    val accessHash: String
)

interface GetListMeet {
    fun getList(accessHash: String)
}

fun Route.listMeet() = post("/create") {
    val params = call.receive<ListParam>()

}
