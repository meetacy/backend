package app.meetacy.backend.usecase.types

import app.meetacy.backend.types.AccessIdentity
import app.meetacy.backend.types.FileIdentity

class FileResponse(
    val accessIdentity: AccessIdentity,
    val fileIdentity: FileIdentity
)