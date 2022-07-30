fun main(){
val test = Some()
    val test_2 = Some()
    val test_3 = Some()
    val test_4 = Some()
}

class Some(){
    //companion object - статичные данные
    companion object{
        var count = 0
    }
    init{
        count++
        println("Создано объектов: $count")
    }
}

