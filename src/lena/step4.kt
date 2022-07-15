//fun main() {
//    var names = arrayOf("Coockie", "Andrew", "Helen", 12)
//    printSome()
//    printSome("Hi", "Some")
//    printSome("Hi", "Some", "hello")
//    printSome("Hi", "Some", "hello", "new")
//    printSome("Hi", *names)
//}
//
//
//fun printSome(item: String = "Hi", vararg word: Any) {
//    print("$item: ")
//    word.forEach { el -> print(" $el") }
//    println("")
//}
//fun main() {
//    var counter = 0
//    while (counter <= 100) {
//        print(" $counter")
//        counter ++
//    }
//}
fun main() {
    var items = 5
    while (items > 0) {
        println(items)
        items--
    }
    do {
        println("items: $items")
    } while (items == 5)
    for (i in 0..20 step 4) {
        println(i)
    }
    for (i in 10 downTo 0 step 3) {
        println(i)
    }
    val x = 20
    if (x !in 5..30) {
        println("Variable: $x")
    }
}
