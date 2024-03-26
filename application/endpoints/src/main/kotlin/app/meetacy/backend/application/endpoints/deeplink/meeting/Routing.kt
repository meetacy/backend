package app.meetacy.backend.application.endpoints.deeplink.meeting

import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.serializable.serialization
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import io.ktor.util.*
import kotlinx.html.*

fun Route.meetingDeeplinks() = get("/m/{id}/{hash}") {
    val id = serialization {
        call.parameters["id"]
            ?.toLong()?.let(::MeetingId)
            ?: return@get call.respondSuccess()
    }

    val hash = serialization {
        call.parameters["hash"]
            ?.let(::AccessHash)
            ?: return@get call.respondSuccess()
    }

    val deeplink = "meetacy://m/${id.long}/${hash.string}"

    call.respondHtml {
        attributes["prefix"] = "og: http://ogp.me/ns/profile#"

        head {
            title(content = "Join Meeting | Meetacy")
            meta(name = "twitter:card", content = "summary_large_image")
            meta(property = "og:type", content = "profile")
            meta(property = "og:title", content = "Join Meeting | Meetacy")
            meta(property = "og:image", content = "https://avatars.githubusercontent.com/u/86232130?s=50")
            meta(property = "og:description", content = "Click the link to view meeting on the map!")
        }

        val javascript = """
            window.setTimeout(() => {
                window.location.replace("https://t.me/meetacy");
            }, 1000);
            
            window.location.replace("$deeplink");
        """

        body {
            javascript(javascript)
        }
    }
}

@KtorDsl
private fun HEAD.meta(
    property: String,
    content: String
) {
    meta {
        attributes["property"] = property
        attributes["content"] = content
    }
}

private fun BODY.javascript(string: String) {
    script(type = "text/javascript") {
        unsafe { +string }
    }
}
