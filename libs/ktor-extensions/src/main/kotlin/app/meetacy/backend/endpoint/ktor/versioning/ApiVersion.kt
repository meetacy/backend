package app.meetacy.backend.endpoint.ktor.versioning


@JvmInline
value class ApiVersion(val int: Int) {
    companion object {
        const val Header = "Api-Version"

        // First versioning was implemented
        val VersioningFeature = ApiVersion(0)
    }
}
