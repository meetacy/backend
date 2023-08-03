@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.database.auth

import app.meetacy.backend.database.auth.TokensStorage
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.tokensStorage: TokensStorage by Dependency

fun DIBuilder.auth() {
    val tokensStorage by singleton { TokensStorage(database) }
}
