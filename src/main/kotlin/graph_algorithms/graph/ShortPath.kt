package graph_algorithms.graph

import Demo
import java.util.*

// BFS Algorithms (Breadth-First Search)
object ShortPath : Demo {

    private val _graph = mapOf(
        "0" to listOf("1", "2"),
        "1" to listOf("5"),
        "2" to listOf("3", "4"),
        "3" to listOf("5"),
        "4" to listOf("5"),
        "5" to listOf("10"),
        "10" to listOf()
    )

    private val _graph2 = mapOf(
        "0" to listOf("1", "6"),
        "1" to listOf("0", "5"),
        "2" to listOf("0", "3", "4"),
        "3" to listOf("2", "5"),
        "4" to listOf("2", "5"),
        "5" to listOf("1", "3", "4", "10"),
        "6" to listOf("0", "10"),
        "10" to listOf("5")
    )

    override fun demonstrate() {
        val shortestWay = findShortestWay("0", "10", _graph2)
        println("ShortestWay = $shortestWay, steps = ${shortestWay.size.dec()}")
    }

    data class Node(
        val value: String, val prevNode: Node?
    )

    // BFS Algorithms
    private fun findShortestWay(nodeA: String, nodeB: String, graph: Map<String, List<String>>): List<String> {
        // Ensure edge case
        if (!graph.containsKey(nodeA) || !graph.containsKey(nodeB)) {
            println("No such a node $nodeA or $nodeB in ${graph.keys}")
            return emptyList()
        }
        if (nodeA.isEmpty() || nodeB.isEmpty() || nodeA == nodeB) {
            println("Node is empty or start == destination")
            return emptyList()
        }

        val visitedNodes = mutableSetOf<String>(nodeA)
        println("Visited nodes: $visitedNodes")

        val nodesToCheck = ArrayDeque<Node>()

        val neighbors = graph[nodeA].orEmpty()
        nodesToCheck += neighbors.map { Node(it, Node(nodeA, null)) }

        while (!nodesToCheck.isEmpty()) {
            println("To check: $nodesToCheck, \n")
            val node = nodesToCheck.removeFirst()
            println("Checking: $node")

            visitedNodes += node.value
            println("Visited nodes: $visitedNodes")

            // Success!
            if (node.value == nodeB) {
                println("Success!\n")
                return findPath(node)
            }

            val nextLevelNeighbors =
                graph[node.value].orEmpty().filter { it !in visitedNodes && !nodesToCheck.any { n -> n.value == it } }
                    .map { Node(it, node) }

            nodesToCheck += nextLevelNeighbors
        }

        // Not found.
        return emptyList()
    }

    private fun findPath(node: Node): List<String> {
        val path = mutableListOf<String>()
        var pathNode: Node? = node

        while (pathNode != null) {
            path += pathNode.value
            pathNode = pathNode.prevNode
        }

        return path.asReversed()
    }

    // BFS Algorithms
    private fun findShortestWaySize(nodeA: String, nodeB: String, graph: Map<String, List<String>>): Int {
        // Ensure edge case
        if (nodeA.isEmpty() || nodeB.isEmpty() || nodeA == nodeB) return 0

        val checkedNodes = mutableSetOf<String>()
        val checkedNodesInLevel = mutableSetOf<String>()

        val nodesToCheck = ArrayDeque<String>()

        nodesToCheck += graph[nodeA].orEmpty()

        var level = 1

        while (!nodesToCheck.isEmpty()) {
            val node = nodesToCheck.removeFirst()

            // Success!
            if (node == nodeB) return level

            checkedNodes += node
            checkedNodesInLevel += node

            if (nodesToCheck.isEmpty()) {
                // Next level
                level++
                checkedNodesInLevel.forEach { checkedNode ->
                    nodesToCheck += graph[checkedNode].orEmpty()
                        // Do not repeat nodes.
                        .filter { childNode -> childNode !in checkedNodes }
                }
                checkedNodesInLevel.clear()
            }
        }

        // Not found.
        return 0
    }

    private fun findMangoSeller(network: Map<String, List<String>>, name: String): String {
        val checkedPersons = mutableListOf<String>()
        val personsToCheck = ArrayDeque<String>()

        personsToCheck += network[name].orEmpty()

        while (!personsToCheck.isEmpty()) {
            val person = personsToCheck.removeFirst()

            if (person in checkedPersons) continue

            if (isPersonSeller(person)) return person

            checkedPersons.add(person)
            personsToCheck += network[person].orEmpty()
        }

        return "Not found"
    }

    private fun isPersonSeller(name: String): Boolean = name.last() == 'm'
}