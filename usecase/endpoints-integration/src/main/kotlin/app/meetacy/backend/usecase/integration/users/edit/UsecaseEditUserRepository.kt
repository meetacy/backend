package app.meetacy.backend.usecase.integration.users.edit

import app.meetacy.backend.endpoint.users.edit.EditUserParams
import app.meetacy.backend.endpoint.users.edit.EditUserRepository
import app.meetacy.backend.endpoint.users.edit.EditUserResult
import app.meetacy.backend.types.map
import app.meetacy.backend.usecase.integration.types.mapToEndpoint
import app.meetacy.backend.usecase.users.edit.EditUserUsecase

class UsecaseEditUserRepository(
    private val usecase: EditUserUsecase
) : EditUserRepository {

    override suspend fun editUser(
        editUserParams: EditUserParams
    ): EditUserResult = with(editUserParams) {
        when (
            val result = usecase.editUser(
                token = token.type(),
                gender = gender.type().map { it?.type() },
                nickname = nickname,
                avatarIdentityOptional = avatarId.type().map { fileIdentity -> fileIdentity?.type() },
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
            is EditUserUsecase.Result.Success ->
                EditUserResult.Success(result.user.mapToEndpoint())
        }
    }
}
