package app.meetacy.backend.usecase.integration.users.avatar.add

import app.meetacy.backend.endpoint.users.avatar.add.AddUserAvatarParams
import app.meetacy.backend.endpoint.users.avatar.add.AddUserAvatarRepository
import app.meetacy.backend.endpoint.users.avatar.add.AddUserAvatarResult
import app.meetacy.backend.usecase.users.avatar.add.AddUserAvatarUsecase

class UsecaseAddUserAvatarRepository(
    private val usecase: AddUserAvatarUsecase
) : AddUserAvatarRepository {
    override suspend fun addAvatar(addUserAvatarParams: AddUserAvatarParams): AddUserAvatarResult = with(addUserAvatarParams) {
        when (usecase.addAvatar(accessIdentity.type(), avatarIdentity.type())) {
            AddUserAvatarUsecase.Result.InvalidAvatarIdentity ->
                AddUserAvatarResult.InvalidUserAvatarIdentity
            AddUserAvatarUsecase.Result.InvalidIdentity ->
                AddUserAvatarResult.InvalidIdentity
            AddUserAvatarUsecase.Result.Success ->
                AddUserAvatarResult.Success
        }
    }
}
