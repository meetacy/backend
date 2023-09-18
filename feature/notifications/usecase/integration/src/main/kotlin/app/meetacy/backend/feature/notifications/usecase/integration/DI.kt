package app.meetacy.backend.feature.notifications.usecase.integration

import app.meetacy.backend.feature.notifications.usecase.integration.add.addNotification
import app.meetacy.backend.feature.notifications.usecase.integration.get.getNotification
import app.meetacy.backend.feature.notifications.usecase.integration.read.readNotification
import app.meetacy.backend.feature.notifications.usecase.integration.view.getNotificationViews
import app.meetacy.backend.feature.notifications.usecase.integration.view.viewNotification
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.notification() {
    addNotification()
    getNotification()
    getNotificationViews()
    readNotification()
    viewNotification()
}
