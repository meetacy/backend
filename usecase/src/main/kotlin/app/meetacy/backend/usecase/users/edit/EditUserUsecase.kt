package app.meetacy.backend.usecase.users.edit

import app.meetacy.backend.types.Optional
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.file.FileId
import app.meetacy.backend.types.file.FileIdentity
import app.meetacy.backend.types.gender.UserGender
import app.meetacy.backend.types.ifPresent
import app.meetacy.backend.types.map
import app.meetacy.backend.types.user.UserId
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
    }

    suspend fun editUser(
        token: AccessIdentity,
        gender: Optional<UserGender?>,
        nickname: String?,
        avatarIdentityOptional: Optional<FileIdentity?>,
    ): Result {
        val userId = authRepository.authorizeWithUserId(token) { return Result.InvalidAccessIdentity }
        if (nickname != null) if (!utf8Checker.checkString(nickname)) return Result.InvalidUtf8String
        var avatarAccessIdentity: FileIdentity? = null
        avatarIdentityOptional.ifPresent { avatarIdentity ->
            avatarIdentity ?: return@ifPresent
            avatarAccessIdentity = filesRepository.checkFileIdentity(avatarIdentity) { return Result.InvalidAvatarIdentity }
        }

        if (listOf(nickname, avatarIdentityOptional).all { it == null }) {
            return Result.NullEditParameters
        }

        // todo я бы писал [fieldName] = .... Так будет норм пон. Еще если дефолтные будут то не будет ошибок
        val fullUser = storage.editUser(
            userId = userId,
            gender = gender,
            nickname = nickname,
            avatarId = avatarIdentityOptional.map { it?.id }
        )
        return Result.Success(
            with(fullUser) {
                UserView(
                    // todo я бы писал [fieldName] = .... Так будет норм пон. Еще если дефолтные будут то не будет ошибок
                    isSelf = true,
                    identity = identity,
                    gender = fullUser.gender,
                    nickname = fullUser.nickname,
                    email = fullUser.email,
                    emailVerified = fullUser.emailVerified,
                    avatarIdentity = avatarAccessIdentity
                )
            }
        )
    }

    interface Storage {
        suspend fun editUser(
            userId: UserId,
            gender: Optional<UserGender?>,
            nickname: String?,
            avatarId: Optional<FileId?>
        ): FullUser
    }
}
