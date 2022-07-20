fun main() {
    val father = Father("Валерий", 59, Salary.MEDIUM)
    //father.sayHello()
    //father.work()

    val mother = Mother("Татьяна", 58, Salary.LOW)
    // mother.sayHello()
    // mother.work()


    val workers = listOf<Worker>(
        father,
        mother,
        // object - анонимный класс
        object : Worker {
            override fun work() {
                println("I'm anonymous")
            }
        }
    )
    sendToWork(workers)
}

fun sendToWork(workers: List<Worker>) {
    workers.forEach {
        it.work()
        (it as? Human)?.sayHello()
    }
}

enum class Gender {
    FEMALE, MALE
}

//IntRange - класс, который позволяет передавать диапазон данных
enum class Salary(val value: IntRange) {
    HIGH(value = 50_000..100_000),
    MEDIUM(value = IntRange(10_000, 49_000)),
    LOW(value = 1000..9000)
}

open class Human(
    open val name: String,
    open val age: Int,
    open val gender: Gender
) {
    open fun sayHello() {
        println(
            """
            Hi! I'm human: 
                name $name
                age $age
                gender $gender 
                """
                .trimIndent()
        )
    }
}

abstract class Woman(
    override val name: String,
    override val age: Int
) : Human(name, age, Gender.FEMALE) {
    override fun sayHello() {
        println(
            """
            Hi! I'm woman: 
                name $name
                age $age
                """
                .trimIndent()
        )
    }
}

class Mother(
    override val name: String,
    override val age: Int,
    val salary: Salary
) : Woman(name, age), Worker {
    override fun sayHello() {
//        super.sayHello()
        println("I'm mother, salary ${salary.value}")
    }

    override fun work() {
        println("I'm a teacher")
    }
}

open class Man(
    override val name: String,
    override val age: Int
) : Human(name, age, Gender.MALE) {

    override fun sayHello() {
        println(
            """
            Hi! I'm man: 
                name $name
                age $age
                """
                .trimIndent()
        )
    }
}

interface Worker {
    fun work()
}

class Father(
    override val name: String,
    override val age: Int,
    val salary: Salary
) : Man(name, age), Worker {
    override fun work() {
        println("I'm an engineer")
    }

    override fun sayHello() {
//        super.sayHello()
        println("I'm father: salary ${salary.value}")
    }
}