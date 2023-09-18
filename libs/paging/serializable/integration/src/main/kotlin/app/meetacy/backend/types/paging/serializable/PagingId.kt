package app.meetacy.backend.types.paging.serializable

import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.serializable.PagingId as PagingIdSerializable

fun PagingIdSerializable.type() = PagingId(string.toLong())

fun PagingId.serializable() = PagingIdSerializable(long.toString())
