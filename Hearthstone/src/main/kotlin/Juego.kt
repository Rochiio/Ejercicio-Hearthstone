import factories.ItemFactory
import factories.PersonajeFactory
import models.Item
import models.enums.ItemsEnum
import models.personajes.Elfos
import models.personajes.Humanos
import models.personajes.Personaje
import models.personajes.Trasgos
import models.tablero.Matriz
import tda.cola.Cola
import tda.cola.ColaImpl
import tda.pila.Pila
import tda.pila.PilaImpl
import utils.Input

class Juego(private var tablero:Matriz) {
    private var tiempo:Int
    private val tamTablero:Int
    private var caballerosDeElrond: Cola = ColaImpl()
    private var amazonasDeIsengard: Cola = ColaImpl()
    private var listaItems: Pila = PilaImpl()


    /**
     * Pedir datos para iniciar el juego
     */
    init {
        tamTablero = Input.pedirTablero()
        tiempo = Input.pedirSegundos()
        addPersonajes()
        addItems()
        tablero.inicializarMatriz(tamTablero)
    }


    fun play(){
        do {
            turnoCaballeros()
            turnoAmazonas()
            if (tiempo%5==0){
                reponer()
            }

            --tiempo
        }while (tiempo>0 && !ganadores() && listaItems.size>0)
    }








    private fun reponer() {
        TODO("Not yet implemented")
    }

    private fun turnoAmazonas() {
        TODO("Not yet implemented")
    }

    private fun turnoCaballeros() {
        val jugadorSacado= caballerosDeElrond.desencolar()
        val fila = (0 until tamTablero).random()
        val columna = (0 until tamTablero).random()
        var itemSelected = tablero.getCasilla(fila,columna).item
        itemSelected?.let { destinado(jugadorSacado, itemSelected, fila, columna) }
    }

    private fun destinado(jugadorSacado: Personaje, itemSelected: Item, fila: Int, columna: Int) {
        when{
            itemSelected.tipo==ItemsEnum.COMIDA -> itemComida(jugadorSacado, fila, columna)
            itemSelected.tipo==ItemsEnum.MATERIAL && jugadorSacado is Humanos -> itemMaterial(jugadorSacado, fila, columna)
            itemSelected.tipo==ItemsEnum.POCION && jugadorSacado is Trasgos -> itemPocion(jugadorSacado, fila, columna)
            itemSelected.tipo==ItemsEnum.HECHIZO && jugadorSacado is Elfos -> itemHechizo(jugadorSacado, fila, columna)
        }
    }

    private fun itemHechizo(hechizero: Elfos, fila: Int, columna: Int) {
        hechizero.inmortalidad += 7
        //TODO problema con el addItem
        tablero[fila][columna].item = null
    }

    private fun itemPocion(trasgo: Trasgos, fila: Int, columna: Int) {
        trasgo.maldad += 2
        //TODO problema con el addItem
        tablero[fila][columna].item = null
    }

    private fun itemMaterial(humano: Humanos, fila: Int, columna: Int) {
        humano.escudo += 5
        //TODO problema con el addItem
        tablero[fila][columna].item = null
    }

    private fun itemComida(jugadorSacado: Personaje, fila: Int, columna: Int) {
        //TODO problema con el addItem
        tablero[fila][columna].item = null
    }

    private fun ganadores():Boolean {
        when{
            caballerosDeElrond.todoACinco() -> return true
            amazonasDeIsengard.todoACinco() -> return true
        }
      return false
    }




    private fun addItems() {
        repeat(200){
            listaItems.add(ItemFactory.itemFactory())
        }
    }

    private fun addPersonajes() {
        repeat(3){
            caballerosDeElrond.add(PersonajeFactory.personajeFactory())
            amazonasDeIsengard.add(PersonajeFactory.personajeFactory())
        }
    }


    /**
     * Función de extensión para saber si todos los integrantes de la batalla tienen sus items a 5 o más
     */
    private fun Cola.todoACinco():Boolean{
        var correcto:Int = 0

        for (i in this){
            if (i.listaItems.size>=5){
                correcto++
            }
        }
        return correcto == 3
    }


}