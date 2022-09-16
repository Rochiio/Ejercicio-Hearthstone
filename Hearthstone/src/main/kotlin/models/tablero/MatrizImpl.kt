package models.tablero

import factories.ItemFactory

class MatrizImpl: Matriz() {

    override fun inicializarMatriz(number: Int) {
        for (i in 0 until number) {
            val column = ArrayList<Casilla>()
            for (j in 0 until number) {
                column.add(j, Casilla(ItemFactory.itemFactory()))
            }
            this.add(column)
        }
    }

    override fun getCasilla(fil: Int, col: Int): Casilla {
        return this[fil][col]
    }

    override fun setCasillaNull(fil: Int, col: Int) {
        this[fil][col].item = null
    }
}