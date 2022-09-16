package models.personajes

import models.Item
import models.enums.PersonajeEnum

class Humanos(
    override val nombre:String="",
    override var vida:Int = 60,
) : Personaje(nombre,vida) {
    var escudo:Int =(1..100).random()
    val tipo: PersonajeEnum = PersonajeEnum.HUMANO
    val caballo: Boolean = ((1..100).random())>50

    override fun mostrarEstado(): String {
        return "Estado del Personaje:  ${super.id}\n" +
                "Nombre: $nombre, Tipo: $tipo, " +
                "Vida: $vida, Escudo: $escudo, Caballo: $caballo \n" +
                "Fecha Creación: ${super.fechaCreacion}, Nivel: ${super.nivel} \n"+
                "Lista de items: ${super.listaOrdenada()}"
    }

    override fun addItem(item: Item) {
        super.addItem(item)
    }
}