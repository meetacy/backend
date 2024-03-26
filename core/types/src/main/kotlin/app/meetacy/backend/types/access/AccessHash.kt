package app.meetacy.backend.types.access

import app.meetacy.backend.constants.ACCESS_HASH_LENGTH

@JvmInline
value class AccessHash(val string: String) {

    init {
        require(string.length == LENGTH) { "Hash length must be $LENGTH, but was ${string.length}" }
    }

    companion object {
        const val LENGTH = ACCESS_HASH_LENGTH
    }
}
