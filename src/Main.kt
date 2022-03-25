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
        StationsAndStates
    )
        .last()
        .demonstrate()
}

interface Demo {

    fun demonstrate()
}