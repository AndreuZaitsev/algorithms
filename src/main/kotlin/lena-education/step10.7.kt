package `lena-education`

//AAAABBBCCXYZDDDDEEEFFFAAAAAABBBBBBBBBBBBBBBBBBBBBBBBBBBB -> A4B3C2XYZD4E3F3A6B2

fun main() {
println(countLetters("BB"))
}

fun countLetters(str: String): String {
    var currentLetter = str[0]
    var count = 1
    var result = ""

    for (letter in str.substring(1)) {
        if (currentLetter == letter) {
            count++
        } else {
            if (count == 1)
                result += currentLetter
            else
                result += "$currentLetter$count"
            count = 1
            currentLetter = letter
        }
    }
    //последние буквы в строке
    if (count == 1)
        result += currentLetter
    else
        result += "$currentLetter$count"

    return result
}