package app.meetacy.backend.feature.auth.usecase.integration.users.edit

import app.meetacy.backend.endpoint.users.edit.EditUserParams
import app.meetacy.backend.endpoint.users.edit.EditUserRepository
import app.meetacy.backend.endpoint.users.edit.EditUserResult
import app.meetacy.backend.feature.auth.usecase.integration.types.mapToEndpoint
import app.meetacy.backend.feature.auth.usecase.users.edit.EditUserUsecase
import app.meetacy.backend.types.optional.map
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.file.type
import app.meetacy.backend.types.serializable.optional.type
import app.meetacy.backend.types.serializable.user.type

class UsecaseEditUserRepository(
    private val usecase: EditUserUsecase
) : EditUserRepository {

    override suspend fun editUser(
        editUserParams: EditUserParams
    ): EditUserResult = with(editUserParams) {
        when (
            val result = usecase.editUser(
                token.type(),
                nickname.type(),
                username.type().map { it?.type() },
                avatarId.type().map { fileIdentity -> fileIdentity?.type() },
            )
        ) {
            EditUserUsecase.Result.InvalidAccessIdentity ->
                EditUserResult.InvalidAccessIdentity
            EditUserUsecase.Result.InvalidAvatarIdentity ->
                EditUserResult.InvalidAvatarIdentity
            EditUserUsecase.Result.InvalidUtf8String ->
                EditUserResult.InvalidUtf8String
            EditUserUsecase.Result.NullEditParameters ->
                EditUserResult.NullEditParameters
            EditUserUsecase.Result.UsernameAlreadyOccupied ->
                EditUserResult.UsernameAlreadyOccupied
            is EditUserUsecase.Result.Success ->
                EditUserResult.Success(result.user.mapToEndpoint())
        }
    }
}
