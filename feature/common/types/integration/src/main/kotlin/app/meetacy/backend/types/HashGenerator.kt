package app.meetacy.backend.types

import app.meetacy.backend.hash.HashGenerator
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.accessHashGenerator() {
    val accessHashGenerator by constant<AccessHashGenerator>(BasicHashGenerator)
}

object BasicHashGenerator : AccessHashGenerator {
    override fun generate(): String = HashGenerator.generate()
}
