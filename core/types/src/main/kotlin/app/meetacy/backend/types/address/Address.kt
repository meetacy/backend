package app.meetacy.backend.types.address

data class Address(
    val country: String,
    val city: String,
    val street: String,
    val placeName: String?
)
