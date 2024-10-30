package com.dam.xevi.pochosubastes.models

import java.time.LocalDate

data class SubastaObject(
    val id: Int,
    var nom: String,
    var descripcio: String,
    var categoria: String,
    var epocaOrigen: String,
    var procedencia: String,
    var venut: Boolean,
    var estat: String,
    var preuInici: Double,
    var preuFinal: Double?,
    var dataSubasta: LocalDate,
    var comprador: String,
    var imatgeUrl: String,

){
}
