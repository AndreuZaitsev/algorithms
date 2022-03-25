package other

import kotlin.math.max
import kotlin.math.min

object SquareDivider {

    fun demonstrate() {
        val a = 1680
        val b = 640
        val squareSize = calculateSquareSize(a, b)
        val subSquaresQuantity = a.parts(squareSize) * b.parts(squareSize)
        println("There're $subSquaresQuantity squares with sides = $squareSize")
    }

    private fun calculateSquareSize(a: Int, b: Int): Int {
        if (a <= 0 || b <= 0) return 0

        val maxSide = max(a, b)
        val minSide = min(a, b)

        // The biggest squares quantity
        val subSquaresNumbers = maxSide / minSide

        return if (maxSide % minSide == 0) {
            // Success! Sides are multiples of
            val subSquareSize = maxSide / subSquaresNumbers
            subSquareSize
        } else {
            // Divide
            val subSquaresSizeSum = minSide * subSquaresNumbers
            val rem = maxSide.minus(subSquaresSizeSum)
            // Recursive call
            calculateSquareSize(rem, minSide)
        }
    }

    private fun Int.parts(smallPart: Int): Int {
        require(this.rem(smallPart) == 0) {
            "$this isn't multiple of $smallPart"
        }
        return this / smallPart
    }
}