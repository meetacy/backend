package app.meetacy.backend.infrastructure.factories

import app.meetacy.backend.database.integration.types.DatabaseValidateRepository
import app.meetacy.backend.endpoint.users.username.validate.ValidateUsernameRepository
import app.meetacy.backend.usecase.integration.users.validate.UsecaseValidateUsernameRepository
import app.meetacy.backend.usecase.validate.ValidateUsernameUsecase
import org.jetbrains.exposed.sql.Database

fun usernameValidationDependencies(db: Database): ValidateUsernameRepository = UsecaseValidateUsernameRepository(
    usecase = ValidateUsernameUsecase(
        validateRepository = DatabaseValidateRepository(db)
    )
)
