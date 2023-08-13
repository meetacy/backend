package app.meetacy.backend.types.integration

import app.meetacy.backend.types.integration.auth.authRepository
import app.meetacy.backend.types.integration.generator.accessHashGenerator
import app.meetacy.backend.types.integration.utf8Checker.utf8Checker
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.common() {
    authRepository()
    accessHashGenerator()
    utf8Checker()
}
