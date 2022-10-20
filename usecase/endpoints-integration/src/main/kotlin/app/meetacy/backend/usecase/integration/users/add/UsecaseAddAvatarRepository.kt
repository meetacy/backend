package app.meetacy.backend.usecase.integration.users.add

import app.meetacy.backend.endpoint.users.avatar.add.AddAvatarParams
import app.meetacy.backend.endpoint.users.avatar.add.AddAvatarRepository
import app.meetacy.backend.endpoint.users.avatar.add.AddAvatarResult
import app.meetacy.backend.usecase.users.add.AddAvatarUsecase

class UsecaseAddAvatarRepository(
    private val usecase: AddAvatarUsecase
) : AddAvatarRepository {
    override suspend fun addAvatar(addAvatarParams: AddAvatarParams): AddAvatarResult = with(addAvatarParams) {
        when (usecase.addAvatar(accessIdentity.type(), avatarIdentity.type())) {
            AddAvatarUsecase.Result.InvalidAvatarIdentity ->
                AddAvatarResult.InvalidAvatarIdentity
            AddAvatarUsecase.Result.InvalidIdentity ->
                AddAvatarResult.InvalidIdentity
            AddAvatarUsecase.Result.Success ->
                AddAvatarResult.Success
        }
    }
}
