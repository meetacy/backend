package app.meetacy.backend.infrastructure.integrations.auth.email

import app.meetacy.backend.database.integration.email.DatabaseConfirmEmailStorage
import app.meetacy.backend.endpoint.auth.email.confirm.ConfirmEmailRepository
import app.meetacy.backend.usecase.email.ConfirmEmailUsecase
import app.meetacy.backend.usecase.integration.email.confirm.UsecaseConfirmEmailRepository
import org.jetbrains.exposed.sql.Database

fun confirmEmailRepository(db: Database): ConfirmEmailRepository = UsecaseConfirmEmailRepository(
    usecase = ConfirmEmailUsecase(
        storage = DatabaseConfirmEmailStorage(db)
    )
)
