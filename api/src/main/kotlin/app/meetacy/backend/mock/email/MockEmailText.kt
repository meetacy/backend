package app.meetacy.backend.mock.email

object MockEmailText {
    fun getText(email: String, confirmHash: String) = """
    Добро пожаловать в приложение Meetacy! 
    Вы решили привязать свой аккаунт к этой почте, чтобы
    подтвердить владние ей, нажмите сюда: 
    https://meetacy.app?email=$email&confirmHash=$confirmHash
    """.trimIndent()
}
