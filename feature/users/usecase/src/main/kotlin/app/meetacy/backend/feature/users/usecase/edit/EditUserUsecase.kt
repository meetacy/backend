package app.meetacy.backend.feature.users.usecase.edit

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.auth.authorizeWithUserId
import app.meetacy.backend.types.files.FileId
import app.meetacy.backend.types.files.FileIdentity
import app.meetacy.backend.types.files.FilesRepository
import app.meetacy.backend.types.files.checkFileIdentity
import app.meetacy.backend.types.optional.Optional
import app.meetacy.backend.types.optional.ifPresent
import app.meetacy.backend.types.optional.map
import app.meetacy.backend.types.users.FullUser
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.types.users.UserView
import app.meetacy.backend.types.users.Username
import app.meetacy.backend.types.utf8Checker.Utf8Checker

class EditUserUsecase(
    private val storage: Storage,
    private val authRepository: AuthRepository,
    private val filesRepository: FilesRepository,
    private val utf8Checker: Utf8Checker
) {

    sealed interface Result {
        class Success(val user: UserView) : Result
        data object InvalidAccessIdentity : Result
        data object InvalidUtf8String : Result
        data object NullEditParameters : Result
        data object InvalidAvatarIdentity : Result
        data object UsernameAlreadyOccupied : Result
    }

    suspend fun editUser(
        token: AccessIdentity,
        nickname: Optional<String> = Optional.Undefined,
        username: Optional<Username?> = Optional.Undefined,
        avatarIdentity: Optional<FileIdentity?> = Optional.Undefined,
    ): Result {
        val userId = authRepository.authorizeWithUserId(token) { return Result.InvalidAccessIdentity }
        return editUser(userId, nickname, username, avatarIdentity)
    }

    suspend fun editUser(
        userId: UserId,
        nickname: Optional<String> = Optional.Undefined,
        username: Optional<Username?> = Optional.Undefined,
        avatarIdentity: Optional<FileIdentity?> = Optional.Undefined,
    ): Result {
        val nicknameValue = nickname.value
        if (nicknameValue != null) if (!utf8Checker.checkString(nicknameValue)) return Result.InvalidUtf8String

        avatarIdentity.ifPresent { identity ->
            if (identity != null) {
                filesRepository.checkFileIdentity(identity) { return Result.InvalidAvatarIdentity }
            }
        }

        val usernameValue = username.value

        if (usernameValue != null && storage.isOccupied(usernameValue)) {
            return Result.UsernameAlreadyOccupied
        }

        if (listOf(nickname, avatarIdentity, username).all { it is Optional.Undefined }) {
            return Result.NullEditParameters
        }

        val fullUser = storage.editUser(
            userId,
            nickname,
            username,
            avatarIdentity.map { it?.id }
        )

        return Result.Success(
            with(fullUser) {
                UserView(
                    isSelf = true,
                    relationship = null,
                    this.identity,
                    this.nickname,
                    this.username,
                    this.email,
                    this.emailVerified,
                    avatarIdentity.value
                )
            }
        )
    }

    interface Storage {
        suspend fun editUser(
            userId: UserId,
            nickname: Optional<String>,
            username: Optional<Username?>,
            avatarId: Optional<FileId?>
        ): FullUser

        suspend fun isOccupied(username: Username): Boolean
    }
}
