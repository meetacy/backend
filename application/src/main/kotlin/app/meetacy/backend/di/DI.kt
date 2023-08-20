@file:Suppress("NAME_SHADOWING")

package app.meetacy.backend.di

import app.meetacy.backend.application.database.database
import app.meetacy.backend.application.usecase.usecase
import app.meetacy.backend.types.integration.types
import app.meetacy.di.builder.di

fun di() = di {
    usecase()
    database()
    types()
}
