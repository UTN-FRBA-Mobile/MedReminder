package com.utn.medreminder.utils

enum class MedAlarmStatus(val char: Char) {
    WAITING('W'),
    FINISHED('F');

    companion object {
        // Function to convert nullable Char to MedAlarmStatus enum
        fun fromChar(char: Char?): MedAlarmStatus? {
            return char?.let { value -> values().find { it.char == value } }
        }
    }
}
