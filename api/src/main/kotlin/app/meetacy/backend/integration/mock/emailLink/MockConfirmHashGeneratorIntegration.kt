package app.meetacy.backend.integration.mock.emailLink

import app.meetacy.backend.mock.generator.MockHashGenerator

object MockConfirmHashGeneratorIntegration : ConfirmHashGenerator {
    override fun generate(): String = MockHashGenerator.generate()
}
