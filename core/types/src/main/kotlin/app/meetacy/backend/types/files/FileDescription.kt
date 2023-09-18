package app.meetacy.backend.types.files

import app.meetacy.backend.types.users.UserId

class FileDescription(
    val ownerId: UserId,
    val fileSize: FileSize,
    val fileIdentity: FileIdentity,
    val fileName: String
)