package app.meetacy.backend.endpoint.integration.mock.generateToken

import app.meetacy.backend.mock.generator.MockHashGenerator

object MockHashGeneratorIntegration : HashGenerator {
    override fun generateHash(): String =
        MockHashGenerator.generate()
}
