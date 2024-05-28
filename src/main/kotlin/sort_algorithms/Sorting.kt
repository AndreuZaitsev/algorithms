package sort_algorithms

import Demo

object QuickSort : Demo {

    override fun demonstrate() {
        val list = (1..99).toList().shuffled()
        val orderedList = qsort(list.toMutableList())

        println("input list = $list")
        println("ordered list = $orderedList")
    }

    private fun qsort(list: MutableList<Int>): MutableList<Int> {
        var pivotIndex = list.size - 1
        var itemIndex = 0

        while (pivotIndex >= itemIndex) {
            val pivot = list[pivotIndex]
            val item = list[itemIndex]

            val isPivotInRightPlace = pivotIndex == itemIndex
            if (isPivotInRightPlace) {
                val left = list.subList(0, pivotIndex)
                val right = list.subList(pivotIndex + 1, list.size)
                return (qsort(left) + pivot + qsort(right)).toMutableList()
            }

            if (pivot < item) {
                // swap and go left
                swap(list, pivotIndex, itemIndex)
                pivotIndex--
            } else if (pivot == item) {
                // the same value, skip item and go for the next
                itemIndex++
            } else if (pivot > item) {
                // the same value, skip item and go for the next
                itemIndex++
            }
        }
        return list
    }

    private fun swap(list: MutableList<Int>, pivotIndex: Int, itemIndex: Int): MutableList<Int> {
        val pivot = list[pivotIndex]
        val item = list[itemIndex]

        val beforePivotIndex = pivotIndex - 1
        val beforePivot = list[beforePivotIndex]

        list[pivotIndex] = item
        list[beforePivotIndex] = pivot

        if (itemIndex != beforePivotIndex) {
            list[itemIndex] = beforePivot
        }

        return list

    }
}