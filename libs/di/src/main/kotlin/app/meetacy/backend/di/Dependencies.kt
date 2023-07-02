package app.meetacy.backend.di

data class Dependencies(val list: List<DependencyPair<*>>) {
    companion object {
        val Empty = Dependencies(emptyList())
    }
}
