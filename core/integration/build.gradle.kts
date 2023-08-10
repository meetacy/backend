plugins {
    id("backend-convention")
}

dependencies {
    api(libs.meetacy.di.core)
    api(projects.core.types)
    api(projects.libs.paging)
}
