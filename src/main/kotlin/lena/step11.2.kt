package lena

fun main() {
    println("Hello! I'm a calculator")
    while (true) {
        println("Enter a number")
        val first = readLine()?.toDoubleOrNull()
        println("Enter a one more number")
        val second = readLine()?.toDoubleOrNull()
        println("Enter an operator (+, -, *, /)")
        val operator = readLine()

        if (first == null || second == null || operator.isNullOrEmpty()) {
            println("Enter valid data")
        } else {
            val result = calculate(first, second, operator)
            println("Result is $result")
        }
    }

}

fun calculate(fir: Double, sec: Double, op: String): Double {
    var res: Double = 0.0
    when (op) {
        "+" -> res = fir + sec
        "-" -> res = fir - sec
        "*" -> res = fir * sec
        "/" -> res = fir / sec
    }
    return res
}