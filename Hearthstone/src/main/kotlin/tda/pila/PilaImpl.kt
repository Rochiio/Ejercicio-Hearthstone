package tda.pila

import models.Item

class PilaImpl: Pila(200) {

    override fun apilar(dato: Item) {
        this.add(dato)
    }

    override fun desapilar(): Item {
        return this.removeAt(this.size-1)
    }


}