package `lena-education`
//Exceptions ana Errors
import java.io.IOException

fun main() {
    val a = 10
    val b = 0
    while(true) {
        println("Введите номер телефона")
        val tel = readln() // or: readLine() ?:""
        try {
            validateTel(tel)
            println("Valid number")
        } catch (e:ValidationException){
            println("Invalid number, ${e.message}")
        }
    }

//    try {
////       println(a / b)
//    } catch (e: ArithmeticException) {
//        println("Попытка деления на 0")
//        e.printStackTrace()
//    }

}
@Throws(ValidationException::class)
fun validateTel(tel: String) {
    if (tel.length != 9) {
        throw ValidationException()
    }
}

object AA {
    @Throws(IOException::class)
    fun foo() {
    }

    @Throws(ArithmeticException::class)
    fun foo2() {
    }
}

class ValidationException(override val message:String = "Validation failed") : Exception(message) {

}