package app.meetacy.backend.database.types

import app.meetacy.backend.types.file.FileIdentity
import app.meetacy.backend.types.file.FileSize
import app.meetacy.backend.types.users.UserId

class DatabaseFileDescription(
    val userId: UserId,
    val fileSize: FileSize?,
    val fileIdentity: FileIdentity,
    val fileName: String
)