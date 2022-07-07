package app.meetacy.backend.mock

object MockAuth {
    /**
     * @return if credentials are valid
     */
    fun authorize(login: String, password: String): Boolean {
        return login == "admin" && password == "admin"
    }
}
