package com.example.unittesting.utils.helper

// ctrl + shift + t -> for test
// alt + ins -> options (inc test)
class Helper {
    fun isPalindrome(input: String): Boolean {
        var i = 0
        var j = input.length - 1
        while (i < j) {
            if (input[i] != input[j]) {
                return false
            }
            i++
            j--
        }
        return true
    }

    fun validatePassword(input: String): String {
        return if (input.isNotBlank() && input.length >= 6 && input.length <= 15) {
            input
        } else {
            "wrong"
        }
    }

    fun reverseString(input: String): String {
        var rev = ""
        for (i in input.length - 1 downTo 0) {
            rev += input[i]
        }
        return rev
    }
}
