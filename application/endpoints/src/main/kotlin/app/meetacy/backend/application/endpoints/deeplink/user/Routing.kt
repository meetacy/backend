package app.meetacy.backend.application.endpoints.deeplink.user

import app.meetacy.backend.endpoint.ktor.respondSuccess
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import io.ktor.util.*
import kotlinx.html.*
import kotlin.collections.set

fun Route.userDeeplinks() = get("/u/{username}") {
    val username = call.parameters["username"] ?: return@get call.respondSuccess()
    val deeplink = "meetacy://view/user?username=${username.escapeHTML()}"

    call.respondHtml {
        attributes["prefix"] = "og: http://ogp.me/ns/profile#"

        head {
            title(content = "@$username | Meetacy")
            meta(name = "twitter:card", content = "summary_large_image")
            meta(property = "og:type", content = "profile")
            meta(property = "og:title", content = "@$username | Meetacy")
            meta(property = "og:image", content = "https://avatars.githubusercontent.com/u/86232130?s=50")
            meta(property = "og:description", content = "Click the link to view profile!")
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
