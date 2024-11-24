package com.utn.medreminder.utils

data class MedAlarmWithItem(
    val idReqCodeAlarm: Long,  // Identificador para el código de la alarma
    val message: String,       // Mensaje asociado con la alarma
    var idAlarmMed: Long? = null,      // ID de la alarma
    val idMed: Long            // ID del medicamento
)