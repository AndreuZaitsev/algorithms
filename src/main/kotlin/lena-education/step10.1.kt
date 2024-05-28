package `lena-education`

fun main() {
    println(testSimple(3, 10))
    println(testString(10))
    println(testNamedArguments(y = 4, z = 5, x = 3))
    testDefaultArgument()
    testDefaultArgument(y = 5)
    testDefaultArgument(3, 7)
//    printEven(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
printEven(*intArrayOf(1,2,3,4,5,6), 7, 8)
}

fun testSimple(x: Int, y: Int) = x + y

fun testString(x: Int): String {
    return "String is $x"
}

fun testNamedArguments(x: Int, y: Int, z: Int): List<Int> {
    return listOf(x, y, z)
}

fun testDefaultArgument(x: Int = 1, y: Int = 2) {
    println(x + y)
}

//vararg
fun printEven(vararg numbers:Int){
    numbers.forEach {
        if(it % 2== 0)
            println(it)
    }
}