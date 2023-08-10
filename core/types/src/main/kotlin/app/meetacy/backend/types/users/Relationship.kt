package app.meetacy.backend.types.users

sealed interface Relationship {
    val string: String

    object None: Relationship {
        override val string: String = "none"
    }

    object Subscriber: Relationship {
        override val string: String = "subscriber"
    }

    object Subscribed: Relationship {
        override val string: String = "subscription"
    }

    object Friend: Relationship {
        override val string: String = "friend"
    }

    companion object {
        fun parse(string: String): Relationship? = when (string) {
            "none" -> None
            "subscriber" -> Subscriber
            "subscribed" -> Subscribed
            "friend" -> Friend
            else -> null
        }
    }
}
