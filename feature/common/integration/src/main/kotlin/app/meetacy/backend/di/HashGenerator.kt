package app.meetacy.di

import app.meetacy.backend.types.AccessHashGenerator
import app.meetacy.di.dependency.Dependency

val DI.accessHashGenerator: AccessHashGenerator by Dependency
