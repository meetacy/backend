package app.meetacy.backend.endpoint.versioning

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import io.ktor.server.application.*
import io.ktor.util.*
import io.ktor.util.pipeline.*

class VersioningBuilder {

    private val versions = mutableListOf<Pair<ApiVersion, suspend (ApiVersion) -> Unit>>()

    fun v(version: ApiVersion, block: suspend (ApiVersion) -> Unit) {
        versions += version to block
    }

    @PublishedApi
    internal suspend fun callAtVersion(apiVersion: ApiVersion) {
        versions.sortedBy { (version) -> version.int }
            .takeWhile { (version) -> version.int <= apiVersion.int }
            .last()
            .second
            .invoke(apiVersion)
    }
}

suspend inline fun PipelineContext<Unit, ApplicationCall>.versioning(
    block: VersioningBuilder.() -> Unit
) {
    val apiVersion = call.extractApiVersion() ?: return call.respondFailure(Failure.ApiVersionIsNotSpecified)

    VersioningBuilder()
        .apply(block)
        .callAtVersion(apiVersion)
}
