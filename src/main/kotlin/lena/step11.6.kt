package lena

// Полиморфизм - множетво форм
fun main() {
    val emailService = EmailService()
    emailService.sendMessage("John@", "Peter@", "Hi!")

    val smsService = SMSService()
    smsService.sendMessage("123456789", "121534567", "Hello!")
}

abstract class MessageService {


    open fun sendMessage(sender: String, receiver: String, message: String) {
        //       if(sender.contains("@") && receiver.contains("@")) {
        println("Send message: \"$message\" from $sender to $receiver")
    }
}

/*    fun sendMessage(sender:Long, receiver:Long, message:String){ /* перегрузка методов: 2 метода с одинаковыми названиями,
        */ но разными параметрами - 1 из способов реализации полиморфизма
        if(sender.toString().length==9 && receiver.toString().length==9) {
            println("Send message: \"$message\" from $sender to $receiver")
        }
    }
} */
// 2-й способ - через классы-насследники: переопределение функций
class EmailService : MessageService() {
    override fun sendMessage(sender: String, receiver: String, message: String) {
        if (sender.contains("@") && receiver.contains("@"))
            super.sendMessage(sender, receiver, message)
    }
}

class SMSService : MessageService() {
    override fun sendMessage(sender: String, receiver: String, message: String) {
        if (sender.length == 9 && receiver.length == 9)
            super.sendMessage(sender, receiver, message)
    }

}