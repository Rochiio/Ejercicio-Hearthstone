package models.tablero


interface MatrizFunciones{
    fun inicializarMatriz(number: Int)
    fun getCasilla(fil:Int, col:Int): Casilla
    fun setCasillaNull(fil:Int, col:Int)
}