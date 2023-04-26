package app.meetacy.backend.types.serialization.gender

import app.meetacy.backend.types.gender.UserGender
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class UserGenderSerializable(val genderName: String) {
    fun type() = UserGender.parseByName(genderName)
}


fun UserGender.serializable() = UserGenderSerializable(this.genderName)
