package app.meetacy.backend.types.gender

sealed interface UserGender {

    val genderName: String


    object Male : UserGender {
        override val genderName = "male"
    }

    object Female : UserGender {
        override val genderName = "female"
    }

    class Other(override val genderName: String) : UserGender


    companion object {
        fun parseByName(gender: String): UserGender = when(gender.lowercase()) {
            Male.genderName -> Male
            Female.genderName -> Female
            else -> Other(gender)
        }
    }

}