package app.meetacy.backend.types.integration

import app.meetacy.backend.types.integration.generator.accessHashGenerator
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.common() {
    accessHashGenerator()
}
