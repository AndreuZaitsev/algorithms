//sealed class - изолированный класс
sealed class DB {

    data class MuSQL(val id: Int, val conn: String) : DB()
    data class MongoDb(val id: Int, val conn: String) : DB()
    data class PostgreSQL(val id: Int, val conn: String, val isDone: Boolean) : DB()
    object Help : DB() {
        val conn = "Connection done"
    }
}

//extension - метод, который расширяет класс (записывается вне класса)
val DB.MongoDb.info: String
    get() = "Похер: $id - $conn"
fun DB.MongoDb.printInfo() {
    println(info)
}

fun filterList(list: List<String>, filter: (String) -> Boolean) {
    list.forEach { el ->
        if (filter(el))
            println(el)
    }
}

fun main() {
    val list = listOf<String>("Java", "PHP")
    filterList(list) { it.endsWith("L") }
    val db = DB.MongoDb(5, "mongo")
    val db_2 = DB.PostgreSQL(5, "mongo", true)
    val db_copy = db.copy(conn = "Done")
    if (db == db_copy)
        println("Они равны")
    else
        println("Они не равны")
    if (db is DB.MongoDb)
        db.printInfo()
}