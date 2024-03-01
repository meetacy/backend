@file:OptIn(UnsafeConstructor::class, UnstableApi::class)

import app.meetacy.sdk.MeetacyApi
import app.meetacy.sdk.production
import app.meetacy.sdk.types.annotation.UnsafeConstructor
import app.meetacy.sdk.types.annotation.UnstableApi
import app.meetacy.sdk.types.auth.Token
import app.meetacy.sdk.types.location.Location
import app.meetacy.sdk.users.AuthorizedSelfUserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

private val matthewToken = Token("284:ZVp2JYU6ZheAjuVxB6XdavVxxHrysT2dpqpjyccZVhOU5XUv5MyVUdLjZmcME90WqNT2Wq9s3e95dpRbDfFTiHOL8E5ZoSG5ZbIrdbMGOtoe5ezo9C3WDrO5e88RWBjlNg9fXt5FSO6G3iG3bEGKu5IAJ6F2qCady3sACamC247RVQJYuRmRAy2b6dDtwLaj2BSisos3CGr90flBsBCOVL5VIjusrWv64Bd0pMUaibHcR6H3xlAs3gycXrAJxwqD")
private val y9san9Token = Token("277:SfEzlwUDCq4UIGhk9d8nvB2UUfkdSZLnyQ8Cb9QDFPhyKEJcqBHeQ7rT2DQFi1wAR6pUUGBQrMY5RrCbadM41te4Nu1jAGuGAlpB5Qbt5ToEjFUEaRTpMCEUnqzL54Gnrrvx2LXN2jAfBj5XzpAQWvU6OkiUw2kQKszRb5FouD5DFLUU6m9UDmvJ612ZAmg4CcQCp8JQulqB2wr7E6rVeHTN7oY2Ewe6CSbNxVxFtbsLClTvzow9gtT7mGH0I6mt")
private val kapToken = Token("296:wUxf4Bjkmqkfv90cHE1Ichzg8GthxBq0nLd00HLcFZT0SMJ0M7XmXtcPFV2moWDJJWvGutmBkvNwQnkjX5tmBYCfeFIsLbat7y4BhwHT5fs9M3j4cfZpJFn9oDIs63rISra1XryC4u8dumHdshDBaaCdpKD6ZPRMYPj57hHwapDOiUd9FVPyw0LnR1ewn6bDzl5cVRTwBKKRrsZBm6t0VKmEfIcnv6TDSujoVxQ3ZfqssNGZkRXe0lsoG0losSvM")
private val yuriToken = Token("314:AOVPXOO7jRCLUMGyYeMXz88KNVohhH3eqKWJ6uV0FU2kXe332eikJJ3xak3fsu7tPJW07kmiiMJ0VdjK0372GxXGimbScJSMZKqiaKfRtKKwQQTDLrr5ty1jfMdVkW20HjaPNbsgtz9G6uukNe4R6ImGSCPvFaE24ignpBG4U7IYL0K43z61BAi6U3cXSdHeryBPyDhB3t8Sq9IM8zJSBkUskR9vCgMGaVhe5bXgncm1tgNhvDLi41uACDj13Doe")
private val heohToken = Token("324:NlusQ9wpMksxV9OHLfvDhkZoV79DhD2IVqNnZOpRc1k2MdmfWA2j30Q6FlBLmexci36TgMm5QlfsUDOCo7eaLRIDO2eJGiEcuwHT24t9hwCPKzYbkPZt4r7zg3Wk2XzkPIOci2GmHNxCfykDM9XUKus8DkwW5jXeE11h7fkJNhH9lpbVrY64jUXGwerRstclpvJgTSWNOOy10WTC82ZDAJuaDIa3LRVBYVmC95VeLfqd9IrSsgIrwZj9fUFezXnm")
private val mixnoToken = Token("325:DlkCxv4PuwbBxHQypQI0CJ9X5fSYby8NEArDAkOQJIq9PGbGwKzOFcdc6kPxraPAZzwQpDVRkhnwdC0hsL8yPCpCbgHYWCsOgLlAMPnenIMRoxnSTc5YqdcQZPQKLyz7qmTmeLQYCZzQW4ZkruROVtcW9QX75w2xC0jK7tHovCKAvhIyWhSg74yxCM4SxCfaVeFxkiYzuzWsoJMJs6iPdTpbNxzE1x5zCYaEH15Yn7CPJNzs5dJkaPzYMXXefuwC")
private val bogdanToken = Token("328:pgkPJyjAPAyvaGRPlBttAKhJf6R2g20xGxxVPYrgKpUmdZxrTwvHpYkGgzEGOOwTdHyXyY1nQrU5tHJZNFLFvpPTS9zgfRYOUpEr69HSU2hamAJ3uCxKUtRoHWL0XNklRbP2M41mGQBazq6TpOCbkMtWV42AioHbalO4Cwg9wl4ePYXfAut4zS1y4UZ299vuNJCKhksqZf3gW4Wc5ZkszoqXMyTzCdEuLLckCWSY8tPmRiYoFdunr9lFsWGAOfuw")
private val demnToken = Token("352:XmB8MzwnH7q0OET6RJyZFKmGk309wFboNsVzSzHESph17HQavDtyGk6oOjshiUemfU9JqRt0JcQ0cMOKg9nAAUbmmaB93EknITpVzdST6igdKEN7D9EbXF2tJkNB73n8sdUkWpjLvkk4DYxd3XdzRf6VeHdyTSqo8arska3WMgd9KtSIPLdCp6Dw56AFB4Do877lf8ELkHAyobUNZFyHAfpiEJfoOA9h5cIPzhJUq14q6vOrZVjWbfsh02a4zIOO")

private val api = MeetacyApi.production()

private val matthew = api.authorized(matthewToken)
private val y9san9 = api.authorized(y9san9Token)
private val kap = api.authorized(kapToken)
private val yuri = api.authorized(yuriToken)
private val heoh = api.authorized(heohToken)
private val mixno = api.authorized(mixnoToken)
private val bogdan = api.authorized(bogdanToken)
private val demn = api.authorized(demnToken)

private val devs = listOf(y9san9, matthew, kap, yuri, heoh, mixno, bogdan, demn)

suspend fun main() {
    kap.friends.location.flow(
        selfLocation = flow {
            while (true) {
                emit(Location(55.800176,37.5212556))
                delay(5_000)
            }
        }
    ).collect()
}

private suspend fun signupDeveloper() {
    val telegram = api.auth.telegram.prelogin()
    println("Auth link: ${telegram.botLink.string}")
    val user = telegram.await().getMe()
    println("User ${user.nickname} authorized!")
    println("Adding friends:")
    addDevsToFriends(user)
    println("Signup completed! Please add the following token to the list of devs:")
    println(user.token)
}

private suspend fun addDevsToFriends(user: AuthorizedSelfUserRepository) {
    val devs = devs.map { it.getMe() }

    for (dev in devs) {
        dev.friends.add(user.id)
        user.friends.add(dev.id)
        println("- ${dev.nickname} added!")
    }
}
