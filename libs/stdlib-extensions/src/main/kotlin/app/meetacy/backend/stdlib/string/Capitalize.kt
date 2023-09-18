package app.meetacy.backend.stdlib.string

import java.util.*

fun String.capitalize(): String {
    return replaceFirstChar { char ->
        if (char.isLowerCase()) char.titlecase(Locale.getDefault()) else char.toString()
    }
}
