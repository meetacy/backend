package app.meetacy.backend.endpoint

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import app.meetacy.backend.domain.User


fun startEndpoints(port: Int, wait: Boolean) =
    embeddedServer(CIO, port) {
        install(ContentNegotiation) {
            json()
        }

        routing {
            get () {
                val users = listOf<User>(
                    User(0, "Abba", "Abbazov Ilshat Zagfyarovich", "Abbaz1962@meetacy.app", "Judge of the Moscow City Court.\n" +
                            "Responsible for the criminal prosecution of\n" +
                            "citizens of the Russian Federation who\n" +
                            "oppose the current political regime in\n" +
                            "Russia.\n"),
                    User(1, "Abram", null, "abr.inv@meetacy.app", "Investigator for Particularly important\n" +
                            "cases of the first investigation department\n" +
                            "of Particularly important cases of the\n" +
                            "Investigation Department of the\n" +
                            "Investigative Committee of the Russian\n" +
                            "Federation for the Kurgan Oblast. Involved\n" +
                            "in the fabrication of the “FBK case”"),
                    User(2, "BGG", "Brakkar Grigory Grigorievich", "BrakkarGG@meetacy.app", "Judge of the Central District Court of\n" +
                            "Novosibirsk. Responsible for the\n" +
                            "prosecution of citizens of the Russian\n" +
                            "Federation who oppose the current\n" +
                            "political regime in Russia\n"),
                    User(3, "AlexV", "Vasin Alexander Anatolievich", "VasA123@meetacy.app", "Judge of the Basmanny District Court of\n" +
                            "Moscow. Responsible for the criminal\n" +
                            "prosecution of citizens of the Russian\n" +
                            "Federation who oppose the current\n" +
                            "political regime in Russia\n"),
                    User(4, "Julia", "Voevodina Yulia Nikolaevna", "VoeVodYN2010@meetacy.app", "Judge of the Central District Court of Tula.\n" +
                            "Responsible for the criminal prosecution of\n" +
                            "citizens of the Russian Federation who\n" +
                            "oppose the current political regime in\n" +
                            "Russia\n"),
                    User(5, "Wolfik", null, "IraWolf@meetacy.app", "Head of the Department for NonCommercial Organizations of the Ministry\n" +
                            "of Justice of the Russian Federation of the\n" +
                            "Astrakhan Oblast. Prevented the\n" +
                            "admission of an opposition party to the\n" +
                            "elections in the interests of illegally\n" +
                            "retaining the power of the ruling regime of\n" +
                            "Vladimir Putin"),
                    User(6, "Dmitriy", "Voronin Dmitry Yurievich", "VDima1959@meetacy.app", "Head of the Legal Department of the CEC\n" +
                            "of the Russian Federation. In the interests\n" +
                            "of illegally retaining the power of the ruling\n" +
                            "regime of Vladimir Putin, he did not allow\n" +
                            "the opposition party to participate in the\n" +
                            "elections\n"),
                    User(7, "LyuftVAffe", "Lyuft Valeriy Arkadievich", "LyuftVAffe@meetacy.app", "Investigator of the Investigation\n" +
                            "Department for the Leninsky\n" +
                            "Administrative District of Omsk,\n" +
                            "Investigation Department of the\n" +
                            "Investigative Committee of the Russian\n" +
                            "Federation for the Omsk Oblast. Involved\n" +
                            "in the fabrication of the “FBK case”\n"),
                    User(8, "Misha", " Melnikov Mikhail Yurievich", "MishaMel@meetacy.app", "Investigator for especially important cases\n" +
                            "of the second department for the\n" +
                            "investigation of especially important cases\n" +
                            "(on crimes against state power and in the\n" +
                            "sphere of the economy) of the\n" +
                            "Investigation Department of the\n" +
                            "Investigative Committee of the Russian\n" +
                            "Federation for the Yaroslavl Oblast.\n" +
                            "Involved in the fabrication of the “FBK\n" +
                            "case”"),
                    User(9, "Miha", null, "MEA1963@meetacy.app", "Investigator for especially important cases\n" +
                            "of the second department for investigating\n" +
                            "especially important cases (on crimes\n" +
                            "against state power and in the sphere of\n" +
                            "the economy) of the Investigation\n" +
                            "Department of the Investigative Committee\n" +
                            "of the Russian Federation for the Belgorod\n" +
                            "Oblast. Involved in the fabrication of the\n" +
                            "“FBK case”\n")
                )
                call.respond(users)
            }
        }
    }.start(wait)

