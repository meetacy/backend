plugins {
    id("backend-convention")
}

dependencies { 
    implementation(projects.core.types)
    implementation(projects.core.database)
}
