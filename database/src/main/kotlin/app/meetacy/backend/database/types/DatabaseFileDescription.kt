package app.meetacy.backend.database.types

import app.meetacy.backend.types.FileIdentity
import app.meetacy.backend.types.FileSize
import app.meetacy.backend.types.UserId

class DatabaseFileDescription(
    val userId: UserId,
    val fileSize: FileSize?,
    val fileIdentity: FileIdentity,
    val fileName: String
)