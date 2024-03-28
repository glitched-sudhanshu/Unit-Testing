package com.example.unittesting.mock

class UserRepository {
    val users =
        listOf(
            User(id = 1, name = "Sudhanshu", email = "rs02@gmail.com", "760191"),
            User(id = 2, name = "Sparsh", email = "sc011@gmail.com", "spardha@$$"),
            User(id = 3, name = "Shaurya", email = "sgupta@gmail.com", "guptaJishh"),
            User(id = 4, name = "Niket", email = "chugh_n@gmail.com", "nik91chugh"),
        )

    fun loginUser(
        email: String,
        password: String,
    ): LoginStatus {
        val users = users.filter { user -> user.email == email }
        return if (users.size == 1) {
            if (users[0].password == password) {
                LoginStatus.SUCCESS
            } else {
                LoginStatus.INVALID_PASSWORD
            }
        } else {
            LoginStatus.INVALID_USER
        }
    }
}
