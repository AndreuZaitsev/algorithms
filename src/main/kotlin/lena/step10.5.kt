package lena

import java.awt.Rectangle

//Class

fun main() {
    val child = Person("Bob", "Jones", 1)
    val p1 = Person("Tom", "Jones", 35, child)
    println(p1.lastName)

    val rectangle1 = Person.Rectangle(5.0, 2.0)
    println("The perimeter is ${rectangle1.perimeter}")

    val rectangle2 = Person.Rectangle(5.0, 2.0)
    println(rectangle1==rectangle2) //true

}

// первичный конструктор
class Person(val firstName: String, val lastName: String, var age: Int) {
    var children: MutableList<Person> = mutableListOf()

    init {
        println("Person is created $firstName")
    }

    //вторичный когструктор
    constructor (firstName: String, lastName: String, age: Int, child: Person) : this(firstName, lastName, age) {
        children.add(child)
    }

    //конструктор без аргументов
  //  constructor() : this("", ", -1")

    data class Rectangle(var hight: Double, var length: Double) {
        var perimeter = (hight + length) * 2

        var test = 1
            get() = field + 1 //this.поле
            set(value) {
                if (value < 0) println("Negative value")
                field = value
            }

        fun area() = hight * length
    }
}