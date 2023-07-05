package app.meetacy.backend.infrastructure.integrations

import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.infrastructure.integrations.auth.auth
import app.meetacy.backend.infrastructure.integrations.files.files

fun DIBuilder.integrations() {
    auth()
    files()
}
