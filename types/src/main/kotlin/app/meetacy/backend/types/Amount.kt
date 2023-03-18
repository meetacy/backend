package app.meetacy.backend.types

@JvmInline
value class Amount(val int: Int) {

    companion object {
        fun parse(int: Int): Amount {
            require(int > 0) { "Positive number expected, but was $int" }
            return Amount(int)
        }
        fun parseOrNull(int: Int): Amount? {
            if (int <= 0) return null
            return Amount(int)
        }
    }
}

val Int.amount: Amount get() = Amount.parse(int = this)
