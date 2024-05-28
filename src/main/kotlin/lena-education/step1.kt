import kotlin.random.Random

fun main() {
    val x = 4
    val y = 5
    val isRect = Random.nextBoolean()
    if (isRect) {
        val square = getRectangleSquare(x, y)
        println("Площадь прямоугольника равна: $square")
    } else {
        val square = getTriangleSquare(x, y)
        println("Площадь треугольника равна: $square")
    }
}

fun getRectangleSquare(x: Int, y: Int): Float {
    return x * y.toFloat()
}

fun getTriangleSquare(x: Int, y: Int): Float {
    return x * y / 2f
}


