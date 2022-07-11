package app.meetacy.backend.endpoint.integration.mock.generateToken

import app.meetacy.backend.mock.generator.MockHashGenerator

object MockAccessHashGeneratorIntegration : AccessHashGenerator {
    override fun generateAccessHash(): String =
        MockHashGenerator.generate()
}
