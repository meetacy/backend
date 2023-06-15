package app.meetacy.backend.database.migrations

import app.meetacy.backend.database.tables
import app.meetacy.database.updater.auto.AutoMigration

fun AutoMigration(
    fromVersion: Int,
    toVersion: Int = fromVersion + 1
): AutoMigration = AutoMigration(tables, fromVersion, toVersion)
