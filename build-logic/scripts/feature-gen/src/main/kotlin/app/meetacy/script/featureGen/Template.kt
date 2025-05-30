package app.meetacy.script.featureGen

import app.meetacy.script.featureGen.stdlibExtensions.capitalize

internal fun endpointsBuildGradleKts() = """
     plugins {
         id("backend-convention")
         id("serialization-convention")
     }
    
     dependencies { 
         implementation(projects.core.endpoints)
     }
     
""".trimIndent()

internal fun endpointsIntegrationBuildGradleKts(featureName: FeatureName) = """
    plugins {
        id("backend-convention")
    }
    
    dependencies {
        implementation(projects.core.endpoints.integration)
        implementation(projects.core.types.serializable.integration)
        implementation(projects.feature.${featureName.camelCase}.endpoints)
        implementation(projects.feature.${featureName.camelCase}.usecase)
    }
    
""".trimIndent()

internal fun endpointsIntegrationRoutingFile(featureName: FeatureName) = """
    package app.meetacy.backend.feature.${featureName.camelCase}.endpoints.integration
    
    import io.ktor.server.routing.*
    import app.meetacy.di.DI
    
    fun Route.${featureName.camelCase}(di: DI) = route("/${featureName.camelCase}") {
        
    }
    
""".trimIndent()

internal fun usecaseBuildGradleKts() = """
     plugins {
         id("backend-convention")
     }
    
     dependencies { 
         implementation(projects.core.types)
         implementation(projects.core.database)
     }
     
""".trimIndent()

internal fun usecaseIntegrationBuildGradleKts(featureName: FeatureName) = """
    plugins {
        id("backend-convention")
    }
    
    dependencies {
        implementation(projects.core.usecase.integration)
        implementation(projects.feature.${featureName.camelCase}.usecase)
        implementation(projects.feature.${featureName.camelCase}.database)
    }
    
""".trimIndent()

internal fun databaseBuildGradleKts() = """
    plugins {
        id("backend-convention")
    }

    dependencies {
        implementation(projects.core.database)
    }
    
""".trimIndent()

internal fun databaseIntegrationBuildGradleKts(featureName: FeatureName) = """
    plugins {
        id("backend-convention")
    }
    
    dependencies {
        implementation(projects.core.database.integration)
        implementation(projects.feature.${featureName.camelCase}.database)
    }
    
""".trimIndent()

internal fun databaseStorageFile(featureName: FeatureName): String {
    val capitalized = featureName.camelCase.capitalize()

    return """
        package app.meetacy.backend.feature.${featureName.camelCase}.database
        
        import org.jetbrains.exposed.sql.*
        
        object ${capitalized}Table : Table() {
            // TODO: Define table schema
        }
        
        class ${capitalized}Storage(private val db: Database) {
            // TODO: Define table operations
        }
    
    """.trimIndent()
}

internal fun diIntegrationFile(module: String, featureName: FeatureName) = """
    package app.meetacy.backend.feature.${featureName.camelCase}.$module.integration
    
    import app.meetacy.di.builder.DIBuilder
    
    fun DIBuilder.${featureName.camelCase}() {
        
    }
    
""".trimIndent()
