package app.meetacy.script.featureGen.stdlibExtensions

import java.util.*

fun String.capitalize(): String {
    return replaceFirstChar { char ->
        if (char.isLowerCase()) char.titlecase(Locale.getDefault()) else char.toString()
    }
}
