package app.meetacy.backend.types.access

import app.meetacy.backend.constants.HASH_LENGTH

@JvmInline
value class AccessToken(val string: String) {

    init {
        require(string.length == LENGTH) { "Token length must be $LENGTH, but was ${string.length}" }
    }

    companion object {
        const val LENGTH = HASH_LENGTH
    }
}
