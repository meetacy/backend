package app.meetacy.backend.feature.files.usecase.integration

import app.meetacy.backend.feature.files.usecase.integration.get.getFileUsecase
import app.meetacy.backend.feature.files.usecase.integration.upload.uploadFileUsecase
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.files() {
    getFileUsecase()
    uploadFileUsecase()
}
