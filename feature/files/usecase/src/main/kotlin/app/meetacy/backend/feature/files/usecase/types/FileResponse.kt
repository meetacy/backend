package app.meetacy.backend.feature.files.usecase.types

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.file.FileIdentity

class FileResponse(
    val accessIdentity: AccessIdentity,
    val fileIdentity: FileIdentity
)