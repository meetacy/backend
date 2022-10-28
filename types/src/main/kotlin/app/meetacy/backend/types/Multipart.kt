package app.meetacy.backend.types

import io.ktor.http.content.*

@JvmInline
value class Multipart(val multiPartData: MultiPartData)