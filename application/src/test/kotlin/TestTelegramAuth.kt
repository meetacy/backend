import app.meetacy.sdk.MeetacyApi
import app.meetacy.sdk.types.annotation.UnstableApi
import app.meetacy.sdk.types.auth.Token
import app.meetacy.sdk.types.url.url
import io.ktor.client.HttpClient
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging

@OptIn(UnstableApi::class)
suspend fun main() {
    val testApi = MeetacyApi(
        baseUrl = "ws://localhost:8080/auth".url,
        httpClient = HttpClient {
            Logging {
                level = LogLevel.ALL
            }
        }
    )

    val permanentToken = testApi.auth.telegram.await(temporalToken = Token("GNkI71wdQEakNpDPFFEoGGefax70p2NDyP9Qt54zZn31UlzXOPRyGYfZEOTcP8tbdFpSRYr4HAFayL899XcgMT6Tn8wkXoEbCDmflNSM3jQ4uVoLOCecHUlP59GZHdPaKBBNGBEsW45nR8QUQgiSCIA9GC744cQygOk8rG3Jxc0A7Sf0weFTLdcrP8C31bWszclUcRSQfPHQbIKzqH1vuF9STaXpNkhCZfoo29cGTFdegYHBeoXh0cB2t8kLwGxY"))
//    val api = MeetacyApi(
//        "http://localhost:8080".url
//    ).authorized(permanentToken)
//    println(api.getMe())
}
