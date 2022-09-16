package tda.cola

import models.personajes.Personaje

class ColaImpl: Cola(3) {
    override fun encolar(dato: Personaje) {
        this.add(dato)
    }

    override fun desencolar(): Personaje {
        return this.removeAt(0)
    }


}