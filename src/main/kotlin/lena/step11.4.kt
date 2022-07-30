package lena

import kotlin.system.exitProcess


fun main() {
    val moscowCoffeeShop = MoscowCoffeeShop(3.0, 4.50, 5.0)
    val newYorkCoffeeShop = NewYorkCoffeeShop(5.0, 7.50, 8.0)
    var currentCoffeeShop: CoffeeShop

    while (true) {
        println("Hello! Choose a city:")
        println("1. Moscow\n2. New York\n\n0. Exit")
        val city = readln()

        currentCoffeeShop = when (city) {
            "1" -> {
                moscowCoffeeShop

            }
            "2" -> {
                newYorkCoffeeShop
            }
            "0" -> break
            else -> {
                println("Error")
                continue
            }
        }
        chooseADrink(currentCoffeeShop)
    }
}

private fun chooseADrink(currentCoffeeShop: CoffeeShop) {
    println("Choose a drink:")
    println("1. Americano\n2. Cappuccino\n3. Latte\n\n0. Show statistics")

    when (readln()) {
        "1" -> {
            currentCoffeeShop.makeAmericano()
            checkAndAskForBiscuits(currentCoffeeShop)
        }
        "2" -> {
            currentCoffeeShop.makeCappuccino()
            checkAndAskForBiscuits(currentCoffeeShop)
        }
        "3" -> {
            currentCoffeeShop.makeLatte()
            checkAndAskForBiscuits(currentCoffeeShop)
        }
        "0" -> currentCoffeeShop.showsStatistics()
        else -> {
            println("Error")
            exitProcess(1)
        }
    }
}

private fun checkAndAskForBiscuits(currentCoffeeShop: CoffeeShop) {
    if (currentCoffeeShop is Biscuits) { // это значит, что переменная curr... имплиментрирует интерфейс Biscuits
        println("Would you like biscuits?")
        println("1. Yes\n2. No")
        val isBiscuits = readln()

        if (isBiscuits == "1") currentCoffeeShop.sellBiscuits()

    }
}


abstract class CoffeeShop(val americanoPrice: Double, val cappuccinoPrice: Double, val lattePrice: Double) {
    var americanoCount = 0
    var cappuccinoCount = 0
    var latteCount = 0

    abstract fun makeAmericano()
    abstract fun makeCappuccino()
    abstract fun makeLatte()

    fun showsStatistics() {
        println("Americano sold: $americanoCount")
        println("Cappuccino sold: $cappuccinoCount")
        println("Latte  sold: $latteCount")

        val money = americanoCount * americanoPrice + cappuccinoCount * cappuccinoPrice + latteCount * lattePrice

        println("Money earned: $money")
    }
}

class MoscowCoffeeShop(americanoPrice: Double, cappuccinoPrice: Double, lattePrice: Double) :
    CoffeeShop(americanoPrice, cappuccinoPrice, lattePrice), Biscuits {
    override fun makeAmericano() {
        americanoCount++
        println("Thanks for order Americano in Moscow")
    }

    override fun makeCappuccino() {
        cappuccinoCount++
        println("Thanks for order Cappuccino in Moscow")
    }

    override fun makeLatte() {
        latteCount++
        println("Thanks for order Latte  in Moscow")
    }

    override fun sellBiscuits() {
        println("Take your biscuits")
    }
}


class NewYorkCoffeeShop(americanoPrice: Double, cappuccinoPrice: Double, lattePrice: Double) :
    CoffeeShop(americanoPrice, cappuccinoPrice, lattePrice) {
    override fun makeAmericano() {
        americanoCount++
        println("Thanks for order Americano in New York")
    }

    override fun makeCappuccino() {
        cappuccinoCount++
        println("Thanks for order Cappuccino in New York")
    }

    override fun makeLatte() {
        latteCount++
        println("Thanks for order Latte  in New York")
    }
}

interface Biscuits {
    fun sellBiscuits()
}
