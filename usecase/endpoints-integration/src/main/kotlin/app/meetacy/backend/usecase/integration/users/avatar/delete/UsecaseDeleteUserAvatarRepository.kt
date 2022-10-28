package app.meetacy.backend.usecase.integration.users.avatar.delete

import app.meetacy.backend.endpoint.users.avatar.delete.DeleteUserAvatarParams
import app.meetacy.backend.endpoint.users.avatar.delete.DeleteUserAvatarRepository
import app.meetacy.backend.endpoint.users.avatar.delete.DeleteUserAvatarResult
import app.meetacy.backend.usecase.users.avatar.delete.DeleteUserAvatarUsecase


class UsecaseDeleteUserAvatarRepository(
    private val usecase: DeleteUserAvatarUsecase
) : DeleteUserAvatarRepository {
    override suspend fun deleteAvatar(deleteUserAvatarParams: DeleteUserAvatarParams): DeleteUserAvatarResult =
        when(usecase.deleteAvatar(deleteUserAvatarParams.accessIdentity.type())) {
            DeleteUserAvatarUsecase.Result.InvalidIdentity ->
                DeleteUserAvatarResult.InvalidAccessIdentity
            DeleteUserAvatarUsecase.Result.Success ->
                DeleteUserAvatarResult.Success
        }
}
