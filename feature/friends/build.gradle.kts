plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.friends.database)
    api(projects.feature.friends.usecase)
    api(projects.feature.friends.endpoints)
    api(projects.feature.friends.database.integrations)
    api(projects.feature.friends.usecase.integrations)
}
