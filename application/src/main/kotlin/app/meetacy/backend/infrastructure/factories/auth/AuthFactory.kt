package app.meetacy.backend.infrastructure.factories.auth

import app.meetacy.backend.database.integration.email.DatabaseConfirmEmailStorage
import app.meetacy.backend.database.integration.email.DatabaseLinkEmailMailer
import app.meetacy.backend.database.integration.email.DatabaseLinkEmailStorage
import app.meetacy.backend.database.integration.tokenGenerator.DatabaseGenerateTokenStorage
import app.meetacy.backend.endpoint.auth.AuthDependencies
import app.meetacy.backend.endpoint.auth.email.EmailDependencies
import app.meetacy.backend.hash.integration.DefaultHashGenerator
import app.meetacy.backend.usecase.auth.GenerateTokenUsecase
import app.meetacy.backend.usecase.email.ConfirmEmailUsecase
import app.meetacy.backend.usecase.email.LinkEmailUsecase
import app.meetacy.backend.usecase.integration.auth.UsecaseTokenGenerateRepository
import app.meetacy.backend.usecase.integration.email.confirm.UsecaseConfirmEmailRepository
import app.meetacy.backend.usecase.integration.email.link.UsecaseLinkEmailRepository
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.utf8.integration.DefaultUtf8Checker
import org.jetbrains.exposed.sql.Database

fun authDependenciesFactory(
    db: Database,
    authRepository: AuthRepository = authRepository(db)
): AuthDependencies = AuthDependencies(
        emailDependencies = EmailDependencies(
            linkEmailRepository = UsecaseLinkEmailRepository(
                usecase = LinkEmailUsecase(
                    storage = DatabaseLinkEmailStorage(db),
                    mailer = DatabaseLinkEmailMailer,
                    hashGenerator = DefaultHashGenerator,
                    authRepository = authRepository
                )
            ),
            confirmEmailRepository = UsecaseConfirmEmailRepository(
                usecase = ConfirmEmailUsecase(
                    storage = DatabaseConfirmEmailStorage(db)
                )
            )
        ),
        tokenGenerateRepository = UsecaseTokenGenerateRepository(
            usecase = GenerateTokenUsecase(
                storage = DatabaseGenerateTokenStorage(DefaultHashGenerator, db),
                tokenGenerator = DefaultHashGenerator,
                utf8Checker = DefaultUtf8Checker
            )
        )
    )
