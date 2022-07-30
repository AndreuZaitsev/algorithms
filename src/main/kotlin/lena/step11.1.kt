package lena

fun main() {
    val names = arrayOf("John", "Bob", "Peter")
    myFunc(names)

    if(readLine()=="Get all names"){
        myFunc(names)
    }

}

fun myFunc(arr: Array<String>) {
    for (name in arr) {
        when (name) {
            "Bob" -> println("Access denied")
            "Peter"-> println("Welcome, Peter")
            else -> println("Who are you?")
        }
    }
}