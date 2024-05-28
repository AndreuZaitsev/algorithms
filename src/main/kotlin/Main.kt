import dynamic_algorithms.Backpack
import dynamic_algorithms.Backpack2
import graph_algorithms.graph.CheapestWay
import graph_algorithms.graph.ShortPath
import graph_algorithms.greedy.StationsAndStates
import other_algorithms.SquareDivider
import search_algorithms.BinarySearch
import sort_algorithms.QuickSort

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
    val a = arrayOf<String>("a", "b", "c")
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