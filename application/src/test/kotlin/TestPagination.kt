import app.meetacy.sdk.types.amount.amount
import app.meetacy.sdk.types.paging.asFlow
import app.meetacy.sdk.types.paging.flatten
import app.meetacy.sdk.types.user.UserId
import kotlinx.coroutines.flow.toList
import kotlin.test.Test

class TestPagination {
    @Test
    fun `test paging iterator`() = runTestServer {
        val self = generateTestAccount()

        val friends = List(20) {
            generateTestAccount(postfix = "Friend #$it")
        }

        for (friend in friends) {
            self.friends.add(friend.id)
            friend.friends.add(self.id)
        }

        val chunkedList = mutableListOf<List<UserId>>()
        for (resultChunk in self.friends.listPaging(chunkSize = 2.amount)) {
            chunkedList.add(resultChunk.map { it.id })
        }

        require(chunkedList.flatten().asReversed() == friends.map { it.id })

        val flattenedList = mutableListOf<UserId>()
        for (resultFriend in self.friends.listPaging(chunkSize = 2.amount).flatten()) {
            flattenedList.add(resultFriend.id)
        }

        require(chunkedList.flatten().asReversed() == friends.map { it.id })

        val result = self.friends
            .listPaging(chunkSize = 2.amount)
            .flatten()
            .asFlow()
            .toList()

        require(result.map { it.id }.asReversed() == friends.map { it.id })
    }
}
