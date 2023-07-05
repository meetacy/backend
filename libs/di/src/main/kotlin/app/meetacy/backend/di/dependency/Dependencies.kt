package app.meetacy.backend.di.dependency

data class Dependencies(val list: List<DependencyPair<*>>) {
    init {
        require(list.distinctBy { it.key }.size == list.size) {
            "Dependencies ambiguity found in $list"
        }
    }

    companion object {
        val Empty = Dependencies(emptyList())
    }
}
