package graph

import java.util.ArrayDeque
import kotlin.test.assertEquals

/**
 * Dijkstra's Algorithm for weighted graph.
 *
 * Positive edges only!
 */
typealias Neighbours = Map<String, Float>
typealias WeightedGraph = Map<String, Neighbours>

object CheapestWay {

    private val _graph: GraphAssert = Graphs.exchangeNegative

    fun demonstrate() {
        val way = findWay(_graph.first)
        assertEquals(_graph.second, way)

        println("The way: ${way.first.joinToString(separator = " -> ")} , summary weight = ${way.second}")
    }

    private fun findWay(
        graph: WeightedGraph,
        nodeA: String = "Start",
        nodeB: String = "End"
    ): Pair<List<String>, Float> {
        val failResult = emptyList<String>() to Float.POSITIVE_INFINITY

        // Edge cases
        if (!graph.containsKey(nodeA) || !graph.containsKey(nodeB)) return failResult
        if (nodeA.isEmpty() || nodeB.isEmpty()) return failResult
        if (nodeA == nodeB) return failResult

        // Traversing
        val costs = graph.neighbours(nodeA).toMutableMap()
        if (costs.containsKey(nodeB)) {
            // Success
            return listOf(nodeA, nodeB) to costs[nodeB]!!
        } else {
            costs[nodeB] = Float.POSITIVE_INFINITY
        }
        println("costs table = $costs")

        val parents = costs.mapValues {
            if (it.key == nodeB) null else nodeA
        }.toMutableMap()
        println("parents table = $parents")

        val processedNodes = mutableSetOf<String>()
        println("processedNodes = $processedNodes")

        var node = findLowestNode(costs, processedNodes)
        while (node != null) {
            val cost = node.value
            val neighbours = graph.neighbours(node.key)
            println("neighbours = $neighbours")

            neighbours.forEach { (key, value) ->
                println("check neighbour = $key=$value")
                val newCost = cost.plus(value)
                if (!costs.containsKey(key)) {
                    costs[key] = newCost
                    parents[key] = node!!.key
                    println("costs table = $costs")
                    println("parents table = $parents")
                }
                val previousCost = costs[key]

                println("prev cost = $previousCost and new = $newCost")
                if (previousCost != null && previousCost > newCost) {
                    // Cheaper way to the key-node is found
                    costs[key] = newCost
                    // Set this node as a parent for the key-node
                    parents[key] = node!!.key

                    println("costs table = $costs")
                    println("parents table = $parents")
                }
            }

            processedNodes += node.key
            println("processedNodes = $processedNodes")
            node = findLowestNode(costs, processedNodes)
        }

        val sumWeight = costs[nodeB]!!
        return buildWay(nodeB, parents) to sumWeight
    }

    private fun WeightedGraph.neighbours(node: String): Neighbours = this[node].orEmpty()

    private fun findLowestNode(costs: Neighbours, processedNodes: Set<String>): Map.Entry<String, Float>? {
        var minEntry: Map.Entry<String, Float>? = null

        costs.entries.forEach { entry ->
            if (entry.value < 0) throw IllegalArgumentException("Negative weight is not supported")

            val notProcessed = entry.key !in processedNodes
            val isLess = minEntry?.let { it.value > entry.value } ?: true
            if (notProcessed && isLess)
                minEntry = entry
        }

        println("lowest node = $minEntry")
        return minEntry
    }

    private fun buildWay(endNode: String, parents: MutableMap<String, String?>): List<String> {
        val way = ArrayDeque<String>()
        way.addLast(endNode)

        var parent = parents[endNode]
        while (parent != null) {
            way.addFirst(parent)
            parent = parents[parent]
        }

        return way.toList()
    }
}

private typealias GraphAssert = Pair<WeightedGraph, (Pair<List<String>, Float>)?>

private object Graphs {
    val first: GraphAssert = mapOf(
        "Start" to mapOf(
            "A" to 6f,
            "B" to 2f
        ),
        "A" to mapOf("End" to 1f),
        "B" to mapOf(
            "A" to 3f,
            "End" to 5f
        ),
        "End" to emptyMap()
    ) to (listOf("Start", "B", "A", "End") to 6f)

    val second: GraphAssert = mapOf(
        "Start" to mapOf(
            "A" to 5f,
            "B" to 2f
        ),
        "A" to mapOf(
            "C" to 4f,
            "D" to 2f
        ),
        "B" to mapOf(
            "A" to 8f,
            "D" to 7f
        ),
        "C" to mapOf(
            "D" to 6f,
            "End" to 3f
        ),
        "D" to mapOf(
            "End" to 1f
        ),
        "End" to emptyMap()
    ) to (listOf("Start", "A", "D", "End") to 8f)

    val cyclic: GraphAssert = mapOf(
        "Start" to mapOf(
            "A" to 10f
        ),
        "A" to mapOf(
            "B" to 20f
        ),
        "B" to mapOf(
            "C" to 1f,
            "End" to 30f
        ),
        "C" to mapOf(
            "A" to 1f
        ),
        "End" to emptyMap()
    ) to (listOf("Start", "A", "B", "End") to 60f)

    val negative: GraphAssert = mapOf(
        "Start" to mapOf(
            "A" to 2f,
            "B" to 2f
        ),
        "A" to mapOf(
            "C" to 2f,
            "End" to 2f
        ),
        "B" to mapOf(
            "A" to 2f
        ),
        "C" to mapOf(
            "B" to -10f,
            "End" to 2f
        ),
        "End" to emptyMap()
    ) to null

    val exchange: GraphAssert = mapOf(
        "Start" to mapOf(
            "Пластинка" to 5f,
            "Постер" to 0f
        ),
        "Пластинка" to mapOf(
            "Гитара" to 15f,
            "Барабан" to 20f
        ),
        "Постер" to mapOf(
            "Гитара" to 30f,
            "Барабан" to 35f
        ),
        "Гитара" to mapOf(
            "End" to 20f
        ),
        "Барабан" to mapOf(
            "End" to 10f
        ),
        "End" to emptyMap()
    ) to (listOf("Start", "Пластинка", "Барабан", "End") to 35f)

    val exchangeNegative: GraphAssert = mapOf(
        "Start" to mapOf(
            "Пластинка" to 5f,
            "Постер" to 0f
        ),
        "Пластинка" to mapOf(
            "Постер" to -7f
        ),
        "Постер" to mapOf(
            "End" to 35f
        ),
        "End" to emptyMap()
    ) to null
}