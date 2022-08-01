package app.meetacy.backend.mock.email

object MockEmailText {
    fun getConfirmationText(email: String, confirmHash: String) = """
    Добро пожаловать в приложение Meetacy! 
    Вы решили привязать свой аккаунт к этой почте, чтобы
    подтвердить владние ей, нажмите сюда: 
    https://meetacy.app?email=$email&confirmHash=$confirmHash
    """.trimIndent()

    fun getOccupiedText() = """
        Здравствуйте! На данный адрес была произведена попытка привязки
        электронной почты. Если это были вы – для начала отвяжите адрес от
        привязанного аккаунта.
        Если это были не вы – просто проигнорируйте сообщение,
        отправитель НЕ УЗНАЛ, что этот адрес уже занят. 
    """.trimIndent()
}
