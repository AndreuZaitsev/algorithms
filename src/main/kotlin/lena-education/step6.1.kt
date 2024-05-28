// interface fluffy squad
// method: attack with param= list of fluffy soldiers
// abstract class: fluffy soldier (name, age): 2 met: abstract (attack), say hello (name)
// 2 classes: cats and dogs
// call tha attack (forEach), filtered (only adults)

fun main() {
    val cat1 = FluffyCat("Coockie", 6)
    val cat2 = FluffyCat("Bunny", 4)
    val cat3 = FluffyCat("Cleo", 7)
    val dog1 = FluffyDog ("Busya", 12)
    val dog2 = FluffyDog ("Barsick", 4)
    val dog3 = FluffyDog("leo", 8)

    val listOfFluffySoldiers: List<FluffySoldier> = listOf(cat1, cat2, cat3, dog1, dog2, dog3)

    val fluffySquad = FluffySquadImpl()
    fluffySquad.attack(listOfFluffySoldiers)
}

interface FluffySquad {
    fun attack(listOfFluffySoldiers: List<FluffySoldier>)
}

class FluffySquadImpl : FluffySquad {
    override fun attack(listOfFluffySoldiers: List<FluffySoldier>) {
        println("Squad attack")

        listOfFluffySoldiers
            .filter {
                it.age > 5
            }
            .forEach {
                it.sayHello()
                it.attack()
            }
    }
}

abstract class FluffySoldier(var name: String, var age: Int) {

    fun sayHello() {
        println("Hi! My name is: $name, age: $age")
    }

    abstract fun attack()
}

class FluffyCat(val catName: String, val catAge: Int) : FluffySoldier(catName, catAge) {

    override fun attack() {
        println("Meow")
    }
}
class FluffyDog (val dogName:String, val dogAge: Int) : FluffySoldier (dogName, dogAge) {

    override fun attack() {
        println("Woof")
    }
}
