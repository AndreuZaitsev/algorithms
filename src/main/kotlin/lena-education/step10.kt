package `lena-education`

fun main(){
    //Variables:
//    val test1 = 3
//    var test2 = 4
//    println("Sum is ${test1+test2}")
    // типы данных: Int (целые числа, 32 бита),
    // Short (целые числа, 16 бит), Long (целые числа, >Int),
    // String (строковый тип), Char (символы), Float (числа с десятичной частью, в конце всегда f), Double (с десятичной частью, >float), Boolean (true/false)
    // forEach
    val items = listOf<String>("apple", "banana", "orange")
//    items.forEach {
//        println(it)
//    } or
    for(item in items)
        println(item)
    //while
    var index = 0
    while(index<items.size) {
        println("Item at $index is ${items[index]}")
        index++
    }
    // Диапазоны
    println(5 in 3..10)

    for(i in 1..10)
        println(i)

    for(i in 1 until 10) // не выводит последнее значение
        println(i)

    for(i in 10 downTo 1)
        println(i)

    for (i in 0..100 step 10)
        println(i)
}