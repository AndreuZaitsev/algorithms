package lena

fun main() {
    val user1 = User("123456", "Doe", 30)
    val user2 = User("123456", "Doe", 25)

    println(user1 == user2) // = println(user1.equals(user2))
    println(user1.hashCode()) // class Any.hashCode - преобразует поля в числа
    println(user2.hashCode())
}

// class Any. toString
data class User(var passportNumber: String, var lastName: String, var age: Int) {
    override fun toString(): String {
        return "User [First name: $passportNumber, Last name: $lastName, Age: $age]"
    }
    // class Any.equals. Если переопределяется метод equals, то необходимо переопределить и hashCode!
    override fun equals(other: Any?): Boolean {
        if (this === other) return true // === проверяет ссылки на объекты в куче
        if (javaClass != other?.javaClass) return false

        other as User

        if (passportNumber != other.passportNumber) return false

        return true
    }

    override fun hashCode(): Int {
        return passportNumber.hashCode()
    }
}

// переписываем функцию, чтобы сранить данные определенной переменной, а не все!
//    override fun equals(other: Any?): Boolean {
//        if (other is User) {
//            return passportNumber == other.passportNumber
//        }
//        return false
//    }
//}
