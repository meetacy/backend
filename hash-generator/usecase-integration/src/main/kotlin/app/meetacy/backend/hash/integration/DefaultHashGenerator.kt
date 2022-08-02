package app.meetacy.backend.hash.integration

import app.meetacy.backend.hash.HashGenerator
import app.meetacy.backend.usecase.types.HashGenerator as UsecaseHashGenerator

object DefaultHashGenerator : UsecaseHashGenerator {
    override fun generate(): String = HashGenerator.generate()
}
