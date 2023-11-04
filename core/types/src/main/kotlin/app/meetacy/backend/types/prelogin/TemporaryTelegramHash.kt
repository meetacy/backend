package app.meetacy.backend.types.prelogin

@JvmInline
value class TemporaryTelegramHash(val string: String) {

    init {
        require(string.length == LENGTH) { "Telegram token length must be 64, but was ${string.length}" }
    }

    companion object {
        const val LENGTH = 64
    }
}
