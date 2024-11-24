package com.utn.medreminder.model

data class MedItem(
    val id: Long? = null,
    val medicamento: String = "",
    val dosis: String = "",
    val frecuencia: String = "",
    val horayFechaDeInicio: String ="",
    val frecuenciaEnHoras: Int = 5,
    val cantidad: Int = 4,
    val alarms: List<MedAlarm>? = null
)
