package app.meetacy.backend.endpoint.users

import app.meetacy.backend.domain.User

object MockUsersProvider {
    fun getUsers(): List<User> = listOf(
        User(
            id = 0,
            username = "Abba",
            fullname = "Abbazov Ilshat Zagfyarovich",
            email = "Abbaz1962@meetacy.app",
            bio = "Judge of the Moscow City Court.\n" +
                    "Responsible for the criminal prosecution of\n" +
                    "citizens of the Russian Federation who\n" +
                    "oppose the current political regime in\n" +
                    "Russia.\n"
        ),
        User(
            id = 1,
            username = "Abram",
            fullname = null,
            email = "abr.inv@meetacy.app",
            bio = "Investigator for Particularly important\n" +
                    "cases of the first investigation department\n" +
                    "of Particularly important cases of the\n" +
                    "Investigation Department of the\n" +
                    "Investigative Committee of the Russian\n" +
                    "Federation for the Kurgan Oblast. Involved\n" +
                    "in the fabrication of the “FBK case”"
        ),
        User(
            id = 2,
            username = "BGG",
            fullname = "Brakkar Grigory Grigorievich",
            email = "BrakkarGG@meetacy.app",
            bio = "Judge of the Central District Court of\n" +
                    "Novosibirsk. Responsible for the\n" +
                    "prosecution of citizens of the Russian\n" +
                    "Federation who oppose the current\n" +
                    "political regime in Russia\n"
        ),
        User(
            id = 3,
            username = "AlexV",
            fullname = "Vasin Alexander Anatolievich",
            email = "VasA123@meetacy.app",
            bio = "Judge of the Basmanny District Court of\n" +
                    "Moscow. Responsible for the criminal\n" +
                    "prosecution of citizens of the Russian\n" +
                    "Federation who oppose the current\n" +
                    "political regime in Russia\n"
        ),
        User(
            id = 4,
            username = "Julia",
            fullname = "Voevodina Yulia Nikolaevna",
            email = "VoeVodYN2010@meetacy.app",
            bio = "Judge of the Central District Court of Tula.\n" +
                    "Responsible for the criminal prosecution of\n" +
                    "citizens of the Russian Federation who\n" +
                    "oppose the current political regime in\n" +
                    "Russia\n"
        ),
        User(
            id = 5,
            username = "Wolfik",
            fullname = null,
            email = "IraWolf@meetacy.app",
            bio = "Head of the Department for NonCommercial Organizations of the Ministry\n" +
                    "of Justice of the Russian Federation of the\n" +
                    "Astrakhan Oblast. Prevented the\n" +
                    "admission of an opposition party to the\n" +
                    "elections in the interests of illegally\n" +
                    "retaining the power of the ruling regime of\n" +
                    "Vladimir Putin"
        ),
        User(
            id = 6,
            username = "Dmitriy",
            fullname = "Voronin Dmitry Yurievich",
            email = "VDima1959@meetacy.app",
            bio = "Head of the Legal Department of the CEC\n" +
                    "of the Russian Federation. In the interests\n" +
                    "of illegally retaining the power of the ruling\n" +
                    "regime of Vladimir Putin, he did not allow\n" +
                    "the opposition party to participate in the\n" +
                    "elections\n"
        ),
        User(
            id = 7,
            username = "LyuftVAffe",
            fullname = "Lyuft Valeriy Arkadievich",
            email = "LyuftVAffe@meetacy.app",
            bio = "Investigator of the Investigation\n" +
                    "Department for the Leninsky\n" +
                    "Administrative District of Omsk,\n" +
                    "Investigation Department of the\n" +
                    "Investigative Committee of the Russian\n" +
                    "Federation for the Omsk Oblast. Involved\n" +
                    "in the fabrication of the “FBK case”\n"
        ),
        User(
            id = 8,
            username = "Misha",
            fullname = " Melnikov Mikhail Yurievich",
            email = "MishaMel@meetacy.app",
            bio = "Investigator for especially important cases\n" +
                    "of the second department for the\n" +
                    "investigation of especially important cases\n" +
                    "(on crimes against state power and in the\n" +
                    "sphere of the economy) of the\n" +
                    "Investigation Department of the\n" +
                    "Investigative Committee of the Russian\n" +
                    "Federation for the Yaroslavl Oblast.\n" +
                    "Involved in the fabrication of the “FBK\n" +
                    "case”"
        ),
        User(
            id = 9,
            username = "Miha",
            fullname = null,
            email = "MEA1963@meetacy.app",
            bio = "Investigator for especially important cases\n" +
                    "of the second department for investigating\n" +
                    "especially important cases (on crimes\n" +
                    "against state power and in the sphere of\n" +
                    "the economy) of the Investigation\n" +
                    "Department of the Investigative Committee\n" +
                    "of the Russian Federation for the Belgorod\n" +
                    "Oblast. Involved in the fabrication of the\n" +
                    "“FBK case”\n"
        )
    )
}
