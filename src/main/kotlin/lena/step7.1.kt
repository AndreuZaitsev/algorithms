fun main() {
    val father = Father(name = "Валерий", age = 59, JobPosition.Engineer(Level.SENIOR, Salary.MEDIUM))
    //father.sayHello()
    //father.work()
    val mother = Mother(name = "Татьяна", age = 58, JobPosition.Teacher(Level.SENIOR, Salary.LOW))
    // mother.sayHello()
    // mother.work()
    val unemployed = Father(name = "Coockie", age = 6, JobPosition.Unemployed)
//    unemployed.sayHello()


    val workers = listOf<Worker>(
        father,
        mother,
        unemployed,
        // object - анонимный класс
        object : Worker {
            override val jobPosition: JobPosition
                get() = JobPosition.Engineer(Level.MIDL, Salary.MEDIUM)

            override fun work() {
                println("I'm anonymous")
            }
        }
    )
    sendToWork(workers)
}

fun List<Worker>.filterWorkers(condition: (Worker) -> Boolean): List<Worker> {
    val destination = mutableListOf<Worker>()
    this.forEachIndexed { index, worker ->
        val isTrueCondition = condition(worker)
        if (isTrueCondition) {
            destination.add(worker)
        }
    }
    return destination
}

fun sendToWork(workers: List<Worker>) {
    println("Has job:")
    workers.filterWorkers() {
//       val position = when (it){
//            is Mother->it.jobPosition
//            is Father->it.jobPosition
//            else->JobPosition.Unemployed
//        }
        it.isAdult(50)
    }
        .forEach {
            (it as? Human)?.sayHello()
            it.work()
            println()
        }

    println("Hasn't job:")
    workers.filter {
//        val position = when (it){
//            is Mother->it.jobPosition
//            is Father->it.jobPosition
//            else->JobPosition.Unemployed
//        }
        val hasJob = it.jobPosition != JobPosition.Unemployed
        !hasJob
    }
        .forEach {
            (it as? Human)?.sayHello()
            it.work()
            println()
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
    override val jobPosition: JobPosition
) : Woman(name, age), Worker {
    override fun sayHello() {
//        super.sayHello()
        println("I'm mother: $name, $age")
    }

    override fun work() {
        println(jobPosition)
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

interface Worker {   /*Интерфейс: не содержит конструктор, не имеет переменных полей, позволяет наследовать
     неограниченное кол-во интерфейсов в отличии от абстрактного класса
     можно наследовать абстрактный класс + интерфейсы. Интерфейсы лучше использовать как средство наследования поведения, а АК - состоянияю */
    val jobPosition: JobPosition
    fun work()
}

fun Worker.isAdult(adultAge: Int): Boolean {
    val age = (this as? Human)?.age ?: 0 //Элвис-оператор
//    val age = (this as? Human)?.age ?: return false
    return age > adultAge
}

class Father(
    override val name: String,
    override val age: Int,
    override val jobPosition: JobPosition
) : Man(name, age), Worker {
    override fun work() {
        println(jobPosition)
    }

    override fun sayHello() {
//        super.sayHello()
        println("I'm father: $name, $age")
    }
}

enum class Level {
    JUNIOR, MIDL, SENIOR
}

sealed class JobPosition {
    data class Engineer(val level: Level, val salary: Salary) : JobPosition()
    data class Teacher(val level: Level, val salary: Salary) : JobPosition()
    object Unemployed : JobPosition() {
        override fun toString(): String {
            return this.javaClass.name
        }
    }
}