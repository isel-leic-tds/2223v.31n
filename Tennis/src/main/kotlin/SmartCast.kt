import kotlin.contracts.*

sealed class User(val name: String) {
    @OptIn(ExperimentalContracts::class)
    fun isAuthenticated(): Boolean {
        contract {
            returns(true) implies (this@User is Authenticated)
            returns(false) implies (this@User is Guest)
        }
        return this is Authenticated
    }

    class Authenticated(name :String) : User(name) {
        fun welcome() = println("Welcome $name")
    }
    class Guest() : User("Unknown") {
        fun prompt() = println("Please login.")
    }
}

fun startPage(user: User) {
    if (user.isAuthenticated())
        user.welcome()
    else
        user.prompt()
    /*
    when (user) {
        is User.Authenticated -> user.welcome()
        is User.Guest -> user.prompt()
    }
     */
    /*
    if (user is User.Authenticated) user.welcome()
    else user.prompt()
    */
}