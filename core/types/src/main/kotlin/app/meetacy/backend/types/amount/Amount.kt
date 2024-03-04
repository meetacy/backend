package app.meetacy.backend.types.amount

import app.meetacy.backend.types.annotation.UnsafeConstructor

@JvmInline
value class Amount @UnsafeConstructor constructor(val int: Int) {
    val orZero: OrZero get() = OrZero.parse(int)

    @OptIn(UnsafeConstructor::class)
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

    @JvmInline
    value class OrZero @UnsafeConstructor constructor(val int: Int) {
        val notZero: OrZero get() = parse(int)
        val notZeroOrNull: OrZero? get() = parseOrNull(int)

        @OptIn(UnsafeConstructor::class)
        companion object {
            fun parse(int: Int): OrZero {
                require(int >= 0) { "Non-negative number expected, but was $int" }
                return OrZero(int)
            }
            fun parseOrNull(int: Int): OrZero? {
                if (int <= 0) return null
                return OrZero(int)
            }
        }
    }
}

val Int.amount: Amount get() = Amount.parse(int = this)
val Int.amountOrZero: Amount.OrZero get() = Amount.OrZero.parse(int = this)
