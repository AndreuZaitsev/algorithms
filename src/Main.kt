import dynamic.Backpack
import dynamic.Backpack2
import graph.CheapestWay
import graph.ShortPath
import greedy.StationsAndStates
import other.SquareDivider
import search.BinarySearch
import sort.QuickSort

fun main() {
    listOf(
        SquareDivider,
        BinarySearch,
        QuickSort,
        ShortPath,
        CheapestWay,
        StationsAndStates,
        Backpack2,
        Backpack
    )
        .last()
//        .demonstrate()
    reverseStringArray()
}


interface Demo {

    fun demonstrate()
}


fun reverseStringArray() {
    val a = arrayOf<String>()
    val size = a.size
    var temp: String

    for (i in 0 until size / 2) {
        val indexFromEnd = size - 1 - i
        temp = a[indexFromEnd]
        a[indexFromEnd] = a[i]
        a[i] = temp
    }
    //Выводим конечный массив в консоль
    for (i in a.indices) {
        print(a[i])
    }
}