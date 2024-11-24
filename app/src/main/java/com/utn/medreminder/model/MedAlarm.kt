package com.utn.medreminder.model

data class MedAlarm (
    val id: Long? = null,
    val dayOfWeek:Int? = null,
    val alarmHour:Int?=null,
    val alarmMinute:Int?=null,
    val alarmDateTime:String?=null,
    val status:Char?=null
)