import kotlin.random.Random

/*
method which returns list of numbers
get numbers via this method
print all the numbers
filter the list to show only odd numbers
print filtered numbers
 */
fun main() {
    val numbers = listOfNumbers()

    numbers.forEach { number ->
        println(number)
    }

    val filteredList = numbers.filter { number ->
        val isOdd = number % 2 == 0
        isOdd
    }
    println("filteredList")
    filteredList.forEach {
        println(it)
    }
}

fun listOfNumbers(): List<Int> {
    return listOf(2, 5, 3, 45, 8, 9)
}


