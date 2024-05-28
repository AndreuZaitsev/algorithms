package `lena-education`
//null
fun main(){
    var a:String = "Hello"
    println(a.length)

    var b:String? = "Test"
    b = null
    b?.length // если переменная равна null, то выведется null, если есть у переменной значение, выведется результат данного метода (никаких NPE)
    // Elvis оператор
    val l = b?.length ?: -1
    b = if((0..10).random()>5) "Hi" else null
    // !! - выбрасывает NPE в случае, если в переменной null
    val t = b!!.length
println(t)

}