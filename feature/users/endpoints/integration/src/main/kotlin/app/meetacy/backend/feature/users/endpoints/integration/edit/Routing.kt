package app.meetacy.backend.feature.users.endpoints.integration.edit

import app.meetacy.backend.feature.users.endpoints.edit.EditUserRepository
import app.meetacy.backend.feature.users.endpoints.edit.EditUserResult
import app.meetacy.backend.feature.users.endpoints.edit.editUser
import app.meetacy.backend.feature.users.usecase.edit.EditUserUsecase
import app.meetacy.backend.types.optional.map
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.file.FileIdentity
import app.meetacy.backend.types.serializable.file.type
import app.meetacy.backend.types.serializable.optional.Optional
import app.meetacy.backend.types.serializable.optional.type
import app.meetacy.backend.types.serializable.users.Username
import app.meetacy.backend.types.serializable.users.serializable
import app.meetacy.backend.types.serializable.users.type
import app.meetacy.di.DI
import io.ktor.server.routing.*

internal fun Route.editUser(di: DI) {
    val usecase: EditUserUsecase by di.getting
    val repository = object : EditUserRepository {
        override suspend fun editUser(
            token: AccessIdentity,
            nickname: Optional<String>,
            username: Optional<Username?>,
            avatarId: Optional<FileIdentity?>
        ): EditUserResult = when (
            val result = usecase.editUser(
                token.type(),
                nickname.type(),
                username.type().map { it?.type() },
                avatarId.type().map { it?.type() }
            )
        ) {
            EditUserUsecase.Result.InvalidUtf8String -> EditUserResult.InvalidUtf8String
            EditUserUsecase.Result.NullEditParameters -> EditUserResult.NullEditParameters
            EditUserUsecase.Result.InvalidAccessIdentity -> EditUserResult.InvalidAccessIdentity
            EditUserUsecase.Result.InvalidAvatarIdentity -> EditUserResult.InvalidAvatarIdentity
            EditUserUsecase.Result.UsernameAlreadyOccupied -> EditUserResult.UsernameAlreadyOccupied
            is EditUserUsecase.Result.Success -> EditUserResult.Success(result.user.serializable())
        }
    }
    editUser(repository)
}
