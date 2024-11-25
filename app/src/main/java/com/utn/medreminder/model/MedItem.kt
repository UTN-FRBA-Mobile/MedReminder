package com.utn.medreminder.model

data class MedItem(
    val id: Long? = null,
    val medicamento: String = "",
    val dosis: String = "",
    val frecuencia: String = "",
    val horayFechaDeInicio: String ="",
    val frecuenciaEnSegundos: Int = 5,
    val cantidad: Int = 4,
    val alarms: List<MedAlarm>? = null,
    val statusCount: StatusCount? = null

)

data class StatusCount(
    val waitingCount: Int = 0,
    val finishedCount: Int = 0,
    val readyCount: Int = 0
)
