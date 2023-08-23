plugins {
    id("backend-convention")
}

dependencies {
    api(projects.core.types.serializable.integration)

    api(projects.feature.invitations.usecase)
    api(projects.feature.invitations.endpoints)

    api(projects.feature.users.usecase.integration) // fixme: move users usecase integration to endpoints

    api(libs.meetacy.di.global)
    api(libs.ktor.server.core)
}
