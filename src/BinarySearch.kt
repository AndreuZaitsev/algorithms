object BinarySearch {

    fun demonstrate() {
        val elements = (1..999).toList()
        val index = binarySearch(elements, 10)
        println(index)
    }

    private fun binarySearch(elements: List<Int>, item: Int): Int? {
        var low = 0
        var high = elements.size - 1

        while (low <= high) {
            val mid = (low + high) / 2
            val guess = elements.get(mid)
            when {
                item == guess -> return mid
                item > guess -> low = mid + 1
                item < guess -> high = mid - 1
            }
        }
        return null
    }
}