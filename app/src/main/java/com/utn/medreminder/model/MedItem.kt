package com.utn.medreminder.model

data class MedItem(
    val id: Long? = null,
    val medicamento: String,
    val dosis: String,
    val frecuencia: String,
    val horaInicio: String

)
