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
        .demonstrate()
}

interface Demo {

    fun demonstrate()
}