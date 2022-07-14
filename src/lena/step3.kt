/*
    map filtered list multiplying each element by 2
    print

    add 10 to the end of the list
    print

    find first element that more than 5
    print this element
 */
fun main() {
    val numbers = listOfNumbers2()
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

    val updatedList = listOf (2, 8)
    println(updatedList.map { it * 2 })

    val mutableList = updatedList.toMutableList()
    mutableList.add(10)
    println(mutableList)
   val firstElement = mutableList.find {
        it > 5
    }
    println(firstElement)
}
fun listOfNumbers2(): MutableList<Int> {
    return mutableListOf(2, 5, 3, 45, 8, 9)
}
