package `lena-education`
// вернуть пересечения массивов, включая повторяющееся значения
fun main() {
//    val list1 = setOf(1, 2, 3, 2, 0)
//    val list2 = setOf(5, 1, 2, 7, 3, 2)
//
//    val common = list1.intersect(list2) // совпадения в двух списках, но без повторений
//    println(common)
    println(getRepeatedIntersection(intArrayOf(1, 2, 3, 2, 0), intArrayOf(5, 1, 2, 7, 3, 2)))
}

fun getRepeatedIntersection(a1: IntArray, a2: IntArray): List<Int> {
    val s1 = a1.toHashSet()
    val s2 = a2.toHashSet()

    val result = mutableListOf<Int>()

    for (el in s1) { // 2
        if (s2.contains(el)) {
            val numOfRepeats = minOf(a1.count { it == el }, a2.count { it == el })
            repeat(numOfRepeats) { result.add(el) }
        }
    }
    return result
}