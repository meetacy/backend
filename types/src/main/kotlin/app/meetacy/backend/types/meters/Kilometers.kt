package app.meetacy.backend.types.meters

@JvmInline
value class Kilometers(val double: Double) {
    operator fun compareTo(other: Kilometers): Int = double.compareTo(other.double)
}

val Double.kilometers: Kilometers get() = Kilometers(this)
val Int.kilometers: Kilometers get() = toDouble().kilometers
