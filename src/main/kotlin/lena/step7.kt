
class NewClass: MainProvider(){
    override val db: String
        get() = "DB not connected"
    override val info: String
        get() = "новый метод"

    override fun printInfo(user: User) {
        super.printInfo(user)
        println("новая функция")
    }
}
