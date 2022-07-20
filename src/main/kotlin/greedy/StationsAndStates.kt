@file:Suppress("SameParameterValue")

package greedy

import Demo

object StationsAndStates : Demo {

    private val states = setOf("ID", "NV", "UT", "WA", "MT", "OR", "CA", "AZ")
    private val stations = mapOf(
        "one" to setOf("ID", "NV", "UT", "XER"),
        "two" to setOf("WA", "ID", "MT"),
        "three" to setOf("OR", "NV", "CA"),
        "four" to setOf("NV", "UT"),
        "five" to setOf("CA", "AZ")
    )

    override fun demonstrate() {
        val neededStates = setOf("ID", "NV", "UT", "WA", "MT", "OR")
        val bestStations = findBestStations(
            stations = stations,
            statesNeeded = neededStates
        )

        println(
            """
            |Input:
            |   States: $states
            |   Stations: $stations
            |   
            |Output:
            |   Best stations: $bestStations   
        """.trimMargin()
        )
    }

    private fun findBestStations(
        stations: Map<String, Set<String>>,
        statesNeeded: Set<String>
    ): Set<String> {
        if (statesNeeded.isEmpty() || stations.isEmpty()) return emptySet()

        val resultStations = mutableSetOf<String>()
        val needed = statesNeeded.toMutableSet()
        val stationsToCheck = stations.toMutableMap()

        while (needed.isNotEmpty()) {
            var bestStation: String? = null
            var coveredStates = setOf<String>()
            var counter = 0
            stationsToCheck.forEach { (station, statesPerStation) ->
                val selection = needed intersect statesPerStation
                if (coveredStates.size < selection.size) {
                    coveredStates = selection
                    bestStation = station
                }
                println("Next station ${counter++}")
            }
            resultStations += bestStation!!
            needed -= coveredStates
            stationsToCheck.remove(bestStation)
            println(
                """
                    |Best Station: $bestStation
                    |Result Stations: $resultStations
                    |Stations To Check: $stationsToCheck
                    |Needed stations: $needed
                    |CoveredStates: $coveredStates
                """.trimMargin()
            )
        }

        return resultStations
    }
}
/**
 *
 * Input:
States: [ID, NV, UT, WA, MT, OR, CA, AZ]
Stations: {one=[ID, NV, UT], two=[WA, ID, MT], three=[OR, NV, CA], four=[NV, UT], five=[CA, AZ]}

Output:
Best stations: [one, two, three]
 */