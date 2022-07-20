enum class Animals{
    cat, dog, bear, lion;
    fun toUpperCase() = name.toUpperCase()
}
fun main() {
//val user = NewClass ()
//    user.printInfo(User())
//    println(user.getConnection())
   // CheckDataType(user)
val user = object : MainProvider(){
    override fun printInfo(user: User) {
        super.printInfo(user)
        println("новая функция")
    }
}
    user.printInfo(User())
val animal = Animals.bear
    when (animal){
        Animals.cat -> println("Cat")
        Animals.bear -> println(animal.toUpperCase())
        else -> println("Someone")
    }
}
fun CheckDataType (obj: MainProvider){
    if (obj is UserInfoProvider) {
        obj.printInfo(User())
    }
    if (obj is DBConnection) {
        println( obj.getConnection())
    }

}

interface UserInfoProvider {
    val info: String
    fun printInfo(user: User) {
            println(info)
            user.printUser()
    }
}
// override - "переписать"
interface DBConnection {
    fun getConnection (): String
}
open class MainProvider : UserInfoProvider, DBConnection {
   protected open val db = "DB connected"
    override val info: String
        get() = "Method was called"
    override fun printInfo(user: User) {
        super.printInfo(user)
        println("Extra kod")
    }

    override fun getConnection(): String {
        return db
    }
    }
