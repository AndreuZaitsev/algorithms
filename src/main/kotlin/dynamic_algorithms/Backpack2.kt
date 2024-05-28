package dynamic_algorithms

import Demo

object Backpack2 : Demo {

    override fun demonstrate() {
        val things = listOf(
            Thing("Guitar", 1, 1500),
            Thing("Tape Recorder", 4, 3000),
            Thing("Laptop", 3, 2000)
        )
        val maxWeight = 4

        val actualPrice = calculateBackpack(things, maxWeight)
        val bestPrice = TagPrice(3500, "Laptop, Guitar")


        println("Best Backpack Price = $actualPrice$")
    }

    private fun calculateBackpack(things: List<Thing>, backpackWeight: Int): TagPrice {
        if (things.isEmpty() || backpackWeight == 0) return TagPrice()

        val weights = (1..backpackWeight).toList()
        val table = Array(things.size) { Array(weights.size) { TagPrice() } }
        printTable(table, things)

        table.forEachIndexed { thingIndex, prices ->
            // Iterate by row
            val thing = things[thingIndex]
            val thingWeight = thing.weight
            val thingPrice = TagPrice(thing.price, thing.name)

            prices.forEachIndexed { columnIndex, currentPrice ->
                // Iterate by column
                val weight = columnIndex + 1

                val previousThingRow = table.getOrNull(thingIndex - 1)
                val knownPrice = previousThingRow?.get(columnIndex) ?: currentPrice

                val priceToSet = if (thingWeight <= weight) {
                    // Set new price to cell if it's greater than the known one
                    val newPrice = knownPrice.max(thingPrice)

                    if (thingWeight == weight) {
                        newPrice
                    } else {
                        val leftWeight = weight - thingWeight
                        if (previousThingRow == null) {
                            newPrice
                        } else {
                            val knownPriceForLeftWeight = previousThingRow[leftWeight - 1]
                            val combinedTagPrice = thingPrice.combine(knownPriceForLeftWeight)
                            newPrice.max(combinedTagPrice)
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

        val bestBackpackPrice = table.last().last()
        return bestBackpackPrice
    }

    private fun Pair<Int, Thing>.max(pair: Pair<Int, Thing>) =
        if (this.first >= pair.first) this else pair

    private data class Thing(
        val name: String = "",
        val weight: Int = 0,
        val price: Int = 0
    )

    private data class TagPrice(
        val price: Int = 0,
        val tag: String = ""
    ) {

        fun max(tagPrice: TagPrice) = if (price >= tagPrice.price) this else tagPrice

        fun combine(tagPrice: TagPrice) = TagPrice(price + tagPrice.price, tag + ", " + tagPrice.tag)
    }


    private fun printTable(priceTable: Array<Array<TagPrice>>, things: List<Thing>, tag: String = "") {
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

            prices.forEach {
                print(" ${it.price}(${it.tag}) |")
            }

            println("\n$lineSeparator")
        }
    }
}