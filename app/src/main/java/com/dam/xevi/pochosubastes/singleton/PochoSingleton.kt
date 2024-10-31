package com.dam.xevi.pochosubastes.singleton

import android.os.Build
import androidx.annotation.RequiresApi
import com.dam.xevi.pochosubastes.models.SubastaObject
import java.time.LocalDate
import java.util.UUID
import java.util.UUID.randomUUID

@RequiresApi(Build.VERSION_CODES.O)
class PochoSingleton private constructor() {
    private val subastes = mutableListOf<SubastaObject>()
    private var selctedObject: SubastaObject? = null
    companion object {
        @Volatile
        private var instance: PochoSingleton? = null
        fun getInstance():PochoSingleton {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = PochoSingleton()
                    }
                }
            }
            return instance!!
        }
    }
    fun addSubasta(obj: SubastaObject) {
        subastes.add(obj)
    }
    fun getSubastas(): MutableList<SubastaObject> {
        return subastes
    }
    fun setSelectedObject(obj: SubastaObject? = null) {
        selctedObject = obj
    }
    fun getSelectedObject(): SubastaObject? {
        return selctedObject
    }


    fun removeFromSubastas():String {
        val e = subastes.remove(selctedObject)
        if (e)
            return selctedObject?.nom + " ha estat eliminat correctament"
        else
            return selctedObject?.nom + " no s'ha pogut eliminar"
        selctedObject = null
    }
    init {
        val subasta1 = SubastaObject(
            id = randomUUID(),
            nom = "Antique Vase",
            descripcio = "A beautiful antique vase from the Ming dynasty.",
            categoria = "Decor",
            epocaOrigen = "Ming Dynasty",
            procedencia = "China",
            venut = false,
            estat = "Good",
            preuInici = 200.0,
            preuFinal = null,
            dataSubasta = LocalDate.of(2024, 11, 10),
            comprador = "",
            imatgeUrl = "https://picsum.photos/200"
        )

        val subasta2 = SubastaObject(
            id = randomUUID(),
            nom = "Vintage Clock",
            descripcio = "A classic vintage clock from the 1920s.",
            categoria = "Furniture",
            epocaOrigen = "1920s",
            procedencia = "Germany",
            venut = true,
            estat = "Excellent",
            preuInici = 150.0,
            preuFinal = 250.0,
            dataSubasta = LocalDate.of(2024, 11, 15),
            comprador = "John Doe",
            imatgeUrl = "https://picsum.photos/200"
        )

        val subasta3 = SubastaObject(
            id = randomUUID(),
            nom = "Medieval Sword",
            descripcio = "An authentic medieval sword from the 14th century.",
            categoria = "Weapons",
            epocaOrigen = "14th Century",
            procedencia = "England",
            venut = false,
            estat = "Excellent",
            preuInici = 500.0,
            preuFinal = null,
            dataSubasta = LocalDate.of(2024, 12, 1),
            comprador = "",
            imatgeUrl = "https://picsum.photos/200"
        )

        val subasta4 = SubastaObject(
            id = randomUUID(),
            nom = "Roman Coin",
            descripcio = "An ancient Roman coin from the Roman Empire era.",
            categoria = "Coins",
            epocaOrigen = "Roman Empire",
            procedencia = "Italy",
            venut = true,
            estat = "Almost Excellent",
            preuInici = 100.0,
            preuFinal = 180.0,
            dataSubasta = LocalDate.of(2024, 11, 25),
            comprador = "Jane Smith",
            imatgeUrl = "https://picsum.photos/200"
        )

        val subasta5 = SubastaObject(
            id = randomUUID(),
            nom = "Old Painting",
            descripcio = "An oil painting from the Renaissance period.",
            categoria = "Art",
            epocaOrigen = "Renaissance",
            procedencia = "France",
            venut = false,
            estat = "Restored",
            preuInici = 1200.0,
            preuFinal = null,
            dataSubasta = LocalDate.of(2024, 12, 5),
            comprador = "",
            imatgeUrl = "https://picsum.photos/200"
        )

        val subasta6 = SubastaObject(
            id = randomUUID(),
            nom = "Native Mask",
            descripcio = "A ceremonial mask from Native American culture.",
            categoria = "Artifacts",
            epocaOrigen = "18th Century",
            procedencia = "USA",
            venut = true,
            estat = "Bad",
            preuInici = 300.0,
            preuFinal = 450.0,
            dataSubasta = LocalDate.of(2024, 11, 30),
            comprador = "Michael Brown",
            imatgeUrl = "https://picsum.photos/200"
        )
        subastes.add(subasta1)
        subastes.add(subasta2)
        subastes.add(subasta3)
        subastes.add(subasta4)
        subastes.add(subasta5)
        subastes.add(subasta6)
    }
}
