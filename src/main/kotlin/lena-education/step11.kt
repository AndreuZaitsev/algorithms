package `lena-education`

fun main() {
//    val s = "Hello World"
//    println(s.first())
//    println(s.last())
//
//    var cardNumber = "1234 5678 9012 3456"
//    println(cardNumber.startsWith("1234")) // true
//    println(cardNumber.endsWith("4567"))// false

//    val a = "Hello"
//    println("The length of \"Hello\" \nis ${a.length}") // \\ для экранирования символа, \n - перенос строки

//    val name: String = "John"
//    val newName = name.decapitalize() // john
//    val name: String = "john"
//    val newName = name.capitalize() //John
//    val name: String = "John"
//    val newName = name.toUpperCase() //JOHN
//    val name: String = "JOHN"
//    val newName = name.toLowerCase() //john
//    println(newName)
//    val name = "\n"
//    println(name.isEmpty()) // учитавыет все символы и прибелы //false
//    println(name.isBlank()) // если есть пробелы или символы - не учитывает их // true
//    val name = "  John Smith  "
//    val newName = name.trimStart() //обрезает пробелы в начале
//    val newName = name.trimEnd() //обрезает пробелы в конце
//    val newName = name.trim()//обрезает пробелы внутри строки, оставляя пробелы между словами
//
//    println(newName)
//    val s = "Today today morning evening today"
//    val newS = s.replace("today", "*****") //заменяет слова
//    val newS = s.toLowerCase().replace("today", "*****") //можно добавлять несколько функций одновременно
//    println(newS)
//
//    val list = "Milk, Bread, Juice, Orange"
//    val ar = list.split(",") // разделение строки на состовляющие
//    println(ar)

//    val input = readLine() //позволяет пользователю вводить данные в консоль
//
//    println(input?.toUpperCase()) // ? т.к. может хранить null

    println("Введите имя")
    val name = readLine()
    println("Имя $name")

    if (name?.toLowerCase()?.capitalize() == "John")
        println("Доступ запрещен")
    else
        println("Добро пожаловать")
}