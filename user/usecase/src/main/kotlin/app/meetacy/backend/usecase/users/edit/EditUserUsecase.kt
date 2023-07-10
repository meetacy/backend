package app.meetacy.backend.usecase.users.edit

import app.meetacy.backend.types.Optional
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.file.FileId
import app.meetacy.backend.types.file.FileIdentity
import app.meetacy.backend.types.ifPresent
import app.meetacy.backend.types.map
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.types.user.Username
import app.meetacy.backend.usecase.types.*

class EditUserUsecase(
    private val storage: Storage,
    private val authRepository: AuthRepository,
    private val filesRepository: FilesRepository,
    private val utf8Checker: Utf8Checker
) {

    sealed interface Result {
        class Success(val user: UserView) : Result
        object InvalidAccessIdentity : Result
        object InvalidUtf8String : Result
        object NullEditParameters : Result
        object InvalidAvatarIdentity : Result
        object UsernameAlreadyOccupied : Result
    }

    suspend fun editUser(
        token: AccessIdentity,
        nickname: Optional<String>,
        usernameOptional: Optional<Username?>,
        avatarIdentityOptional: Optional<FileIdentity?>,
    ): Result {
        val nicknameValue = nickname.value

        val userId = authRepository.authorizeWithUserId(token) { return Result.InvalidAccessIdentity }
        if (nicknameValue != null) if (!utf8Checker.checkString(nicknameValue)) return Result.InvalidUtf8String

        avatarIdentityOptional.ifPresent { identity ->
            if (identity != null) {
                filesRepository.checkFileIdentity(identity) { return Result.InvalidAvatarIdentity }
            }
        }

        val usernameValue = usernameOptional.value

        if (usernameValue != null && storage.isOccupied(usernameValue)) {
            return Result.UsernameAlreadyOccupied
        }

        if (listOf(nickname, avatarIdentityOptional, usernameOptional).all { it is Optional.Undefined }) {
            return Result.NullEditParameters
        }

        val fullUser = storage.editUser(
            userId,
            nickname,
            usernameOptional,
            avatarIdentityOptional.map { it?.id }
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
                    avatarIdentityOptional.value
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
