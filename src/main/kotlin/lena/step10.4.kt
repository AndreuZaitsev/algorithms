package lena

//Lambda
fun main() {
    val a = listOf(1, 2, 3, 4, 5)
    a.forEach {
        println(it)
    }
    println(a.map { it * 2 })
    println(a.filter { it % 2 == 0 })
    println(a.reduce { sum, it -> sum + it }) // =sum()

    a.sortedByDescending { it } // сортирует по убыванию
    a.any { it > 10 } //в данном случае вернет false
    a.all { it < 10 } // в данном случае вернет true
    a.sum()

    val numbers = listOf(1, 3, -4, 5, -11)
    val (positive, negative) = numbers.partition { it > 0 } // делит список на  коллекции по определенному признаку
    println(positive)
    println(negative)

    val result = listOf("a", "b", "ab", "ccc", "da").groupBy { it.length }
    println(result) //HashMap

}
