
inline fun <reified T : Throwable> assertThrows(block: () -> Unit) {
    var throwable: T? = null

    try {
        block()
    } catch (t: Throwable) {
        if (t is T) {
            throwable = t
        } else {
            throw t
        }
    }

    if (throwable == null) {
        error("${T::class.simpleName} was expected to be thrown, but nothing was")
    }
}
