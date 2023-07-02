package app.meetacy.backend.di

/**
 * Throws if some dependencies are inaccessible.
 * This eliminates cons of having service locator
 * instead of compile-time DI
 */
fun Dependencies.checkDependencies() {
    val di = DI(dependencies = this)

    for (dependency in di.dependencies.list) {
        di.get(dependency.key)
    }
}

fun DI.checkDependencies() {
    dependencies.checkDependencies()
}
