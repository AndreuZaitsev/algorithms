package lena

//сгруппировать по одинаковым буквам ["eat", "tea", "tan","ate", "nat", "bat"]

fun main() {
    println(groupWords(arrayOf("eat", "tea", "tan", "ate", "nat", "bat" )))
}

fun groupWords(words: Array<String>): List<List<String>> {
    val result: MutableList<List<String>> = mutableListOf()

    val map = mutableMapOf<String, MutableList<String>>()

    for (word in words) {
        val sortedWords = word.toCharArray().sorted()
            .joinToString("") // вначале строку преобразуем в массив символов, затем сортируем, затем преобразовываем в строку

        if (map.containsKey(sortedWords))
            map[sortedWords]?.add(word)
        else
            map[sortedWords] = mutableListOf(word)
    }
    for ((key, value) in map) {
        result.add(value)
    }
    return result
}