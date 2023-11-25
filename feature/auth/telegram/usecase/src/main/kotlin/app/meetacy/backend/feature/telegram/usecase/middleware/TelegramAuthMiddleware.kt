package app.meetacy.backend.feature.telegram.usecase.middleware

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.auth.telegram.TemporaryTelegramHash
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first

class TelegramAuthMiddleware(
    private val storage: Storage
) {
    private val events = MutableSharedFlow<Pair<TemporaryTelegramHash, AccessIdentity>>()

    suspend fun saveAccessIdentity(
        accessIdentity: AccessIdentity,
        telegramHash: TemporaryTelegramHash
    ) {
        events.emit(value = telegramHash to accessIdentity)
        storage.addAccessIdentity(telegramHash, accessIdentity)
    }

    suspend fun getAccessIdentity(
        telegramHash: TemporaryTelegramHash
    ): AccessIdentity = coroutineScope {
        val subscription = async(start = CoroutineStart.UNDISPATCHED) {
            events.first { (hash) -> hash == telegramHash }.second
        }

        val accessIdentity = storage.getAccessIdentity(telegramHash)
            ?: return@coroutineScope subscription.await()

        subscription.cancel()
        return@coroutineScope accessIdentity
    }

    interface Storage {
        suspend fun addAccessIdentity(
            telegramHash: TemporaryTelegramHash,
            accessIdentity: AccessIdentity
        )
        suspend fun getAccessIdentity(
            telegramHash: TemporaryTelegramHash
        ): AccessIdentity?
    }
}
