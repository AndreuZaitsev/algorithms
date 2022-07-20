fun main() {
    launchCatsSquad()
}

private fun launchCatsSquad() {
    val cat1 = Cat("Coockie", 6)
    val cat2 = Cat("Cleo", 2)
    val cat3 = Cat("Bunny", 7)
    val catTeam = listOf(cat1, cat2, cat3)
    val adultCats = catTeam.filter {
        it.catAge > 5
    }
    adultCats.forEach {
        println(it.catName)
    }
}

private class Cat(
    val catName: String,
    val catAge: Int
)
