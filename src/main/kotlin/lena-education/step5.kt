 class User(var firstName: String = "Peter", var secondName: String = "Parker") {
//    constructor(): this ("Peter", "Parker"){
//        println("Данные были установлены")
//    }
//constructor(firstName: String): this (firstName, "Parker")
var login: String? = null
    set (value){
        if (value == "Codi"){
            field = "None"
        }
        else field = value
        println("Значение переменной: $field")

    }
    get () {
        println("переменная равна: $field")
        return field
    }
     fun printUser (){
        println("$firstName $secondName")
    }
}
// или прописать через init:
// class User( _firstName: String,  _secondName: String) {
//var firstName: String
//var secondName: String
// init {
// firstName = _firstName
// secondName = _secondName
// }
// }
//    var firstName: String = "Alex"
//    var secondName: String = "Marley"
//    fun printUser () {
//        println("$firstName $secondName")
//    }
//}
// если переменная равна значению null, можно прописать ее значение как "неищвестно" ч/з get
// get () {
// val loginField = field ?: "неизвестно"
//println ("Переменная равна: $loginField")
// return field
// }
// при val set не доступно!

fun main() {
    val alex = User ("Alex", "Marley")
    val john = User ()
    println(alex.firstName)
    alex.login = "Codi"
    alex.login
    alex.printUser()
    // peter.login
//    alex.firstName = "Alexander"
//    alex.printUser()
}

