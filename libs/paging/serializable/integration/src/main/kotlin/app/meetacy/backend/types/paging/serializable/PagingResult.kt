package app.meetacy.backend.types.paging.serializable

import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.paging.serializable.PagingResult as PagingResultSerializable

fun <T> PagingResultSerializable<T>.type() =
    PagingResult(data, nextPagingId?.type())

fun <T> PagingResult<T>.serializable() =
    PagingResultSerializable(data, nextPagingId?.serializable())
