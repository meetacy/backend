package app.meetacy.backend.mock.integration

import app.meetacy.backend.mock.generator.MockHashGenerator
import app.meetacy.backend.usecase.types.HashGenerator

object MockHashGeneratorIntegration : HashGenerator {
    override fun generate(): String = MockHashGenerator.generate()
}