package app.meetacy.backend.types

import app.meetacy.backend.constants.HASH_LENGTH
import app.meetacy.backend.hash.HashGenerator
import app.meetacy.backend.types.generator.AccessHashGenerator
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.accessHashGenerator() {
    val accessHashGenerator by constant<AccessHashGenerator>(BasicHashGenerator)
}

object BasicHashGenerator : AccessHashGenerator {
    override fun generate(): String = HashGenerator.generate(HASH_LENGTH)
}
