package dynamic

import Demo
import kotlin.math.max
import kotlin.test.assertEquals

object Backpack : Demo {

    override fun demonstrate() {
        val things = listOf(
            Thing("Guitar", 1, 1500),
            Thing("Tape Recorder", 4, 3000),
            Thing("Laptop", 3, 2000)
        )
        val maxWeight = 4

        val actualPrice = calculateBackpack(things, maxWeight)
        val bestPrice = 3500

        assertEquals(bestPrice, actualPrice)
        println("Best Backpack Price = $actualPrice$")
    }

    private fun calculateBackpack(things: List<Thing>, backpackWeight: Int): Int {
        if (things.isEmpty() || backpackWeight == 0) return 0

        val weights = (1..backpackWeight).toList()
        val table = Array(things.size) { Array(weights.size) { 0 } }
        printTable(table, things)

        table.forEachIndexed { thingIndex, prices ->
            // Iterate by row
            val thing = things[thingIndex]
            val thingWeight = thing.weight
            val thingPrice = thing.price

            prices.forEachIndexed { columnIndex, currentPrice ->
                // Iterate by column
                val weight = columnIndex + 1

                val previousThingRow = table.getOrNull(thingIndex - 1)

                val knownPrice = previousThingRow?.get(columnIndex) ?: currentPrice

                val priceToSet =
                    if (thingWeight <= weight) {
                        // Set new price to cell if it's greater than the known one
                        val newPrice = max(thingPrice, knownPrice)

                        if (thingWeight == weight)
                            newPrice
                        else {
                            val leftWeight = weight - thingWeight
                            if (previousThingRow == null) {
                                newPrice
                            } else {
                                val knownPriceForLeftWeight = previousThingRow[leftWeight - 1]
                                max(newPrice, knownPriceForLeftWeight + thingPrice)
                            }
                        }
                    } else {
                        // Set known price to cell
                        knownPrice
                    }

                table[thingIndex][columnIndex] = priceToSet
                printTable(table, things, "Iteration ${thing.name} $weight")
            }
        }
        printTable(table, things)

        traceBackPack(things, table)

        val bestBackpackPrice = table.last().last()
        return bestBackpackPrice
    }

    private fun traceBackPack(things: List<Thing>, table: Array<Array<Int>>) {
        val backpack = mutableListOf<Thing>()

        table.forEachIndexed { index, prices ->
            prices.forEachIndexed { index, i ->

            }
        }

        println(backpack)
    }

    private data class Thing(
        val name: String = "",
        val weight: Int = 0,
        val price: Int = 0
    )


    private fun printTable(priceTable: Array<Array<Int>>, things: List<Thing>, tag: String = "") {
        val indentOffset = 50
        println("\nTABLE $tag")

        val lineSeparator = "-".repeat(50 + indentOffset)
        println("\n$lineSeparator")

        val indentProvider: (Int) -> String = { offset -> " ".repeat(20 + indentOffset - offset) }
        priceTable.forEachIndexed { index, prices ->
            val thing = things[index]
            val thingName = "${thing.name}(${thing.weight}Kg ${thing.price}$)"
            val indent = indentProvider.invoke(thingName.length)
            print("$thingName:$indent")
            prices.forEach { price ->
                print(" $price |")
            }
            println("\n$lineSeparator")
        }
    }
}
