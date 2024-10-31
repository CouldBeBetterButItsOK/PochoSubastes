package com.dam.xevi.pochosubastes.models

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.util.UUID

data class SubastaObject(
    var id: UUID,
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
): Parcelable {
    @RequiresApi(Build.VERSION_CODES.O)
    constructor(parcel: Parcel) : this(
        id = UUID.fromString(parcel.readString()!!),
        nom = parcel.readString()!!,
        descripcio = parcel.readString()!!,
        categoria = parcel.readString()!!,
        epocaOrigen = parcel.readString()!!,
        procedencia = parcel.readString()!!,
        venut = parcel.readByte() != 0.toByte(),
        estat = parcel.readString()!!,
        preuInici = parcel.readDouble(),
        preuFinal = parcel.readValue(Double::class.java.classLoader) as? Double,
        dataSubasta = LocalDate.parse(parcel.readString()),
        comprador = parcel.readString()!!,
        imatgeUrl = parcel.readString()!!
    )
    fun clone(subastaObject: SubastaObject){
        this.id = subastaObject.id
        this.nom = subastaObject.nom
        this.descripcio = subastaObject.descripcio
        this.categoria = subastaObject.categoria
        this.epocaOrigen = subastaObject.epocaOrigen
        this.procedencia = subastaObject.procedencia
        this.venut = subastaObject.venut
        this.estat = subastaObject.estat
        this.preuInici = subastaObject.preuInici
        this.preuFinal = subastaObject.preuFinal
        this.dataSubasta = subastaObject.dataSubasta
        this.comprador = subastaObject.comprador
        this.imatgeUrl = subastaObject.imatgeUrl
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id.toString())
        parcel.writeString(nom)
        parcel.writeString(descripcio)
        parcel.writeString(categoria)
        parcel.writeString(epocaOrigen)
        parcel.writeString(procedencia)
        parcel.writeByte(if (venut) 1 else 0)
        parcel.writeString(estat)
        parcel.writeDouble(preuInici)
        parcel.writeValue(preuFinal)
        parcel.writeString(dataSubasta.toString())
        parcel.writeString(comprador)
        parcel.writeString(imatgeUrl)
    }
    override fun describeContents(): Int {
        return 0
    }
    companion object CREATOR : Parcelable.Creator<SubastaObject> {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun createFromParcel(parcel: Parcel): SubastaObject {
            return SubastaObject(parcel)
        }
        override fun newArray(size: Int): Array<SubastaObject?> {
            return arrayOfNulls(size)
        }
    }

}
