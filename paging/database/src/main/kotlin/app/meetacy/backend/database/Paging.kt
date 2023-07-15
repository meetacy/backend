package app.meetacy.backend.database

import app.meetacy.backend.types.paging.PagingId
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder.less

infix fun Column<Long>.paging(pagingId: PagingId?): Op<Boolean> {
    pagingId ?: return Op.TRUE
    return this less pagingId.long
}
