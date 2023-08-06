package app.meetacy.backend.infrastructure.integration.files

import app.meetacy.backend.infrastructure.integration.files.get.getFileRepository
import app.meetacy.backend.infrastructure.integration.files.upload.uploadFileRepository
import app.meetacy.di.builder.DIBuilder


fun DIBuilder.files() {
    getFileRepository()
    uploadFileRepository()
}
