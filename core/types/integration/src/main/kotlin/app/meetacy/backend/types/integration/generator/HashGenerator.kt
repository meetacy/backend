package app.meetacy.backend.types.integration.generator

import app.meetacy.backend.constants.ACCESS_HASH_LENGTH
import app.meetacy.backend.hash.HashGenerator
import app.meetacy.backend.types.generator.AccessHashGenerator
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.accessHashGenerator() {
    val accessHashGenerator by singleton<AccessHashGenerator> {
        object : AccessHashGenerator {
            override fun generate(): String = HashGenerator.generate(ACCESS_HASH_LENGTH)
        }
    }
}
