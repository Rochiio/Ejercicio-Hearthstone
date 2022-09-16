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
        informe()
    }

    private fun informe() {
       val listaCaballeros = caballerosDeElrond.sortedBy { listaItems.size }
  //      println(caballerosDeElrond[0].mostrarEstado())
//        println(lista[0].mostrarEstado())

        println("Informe de resultados: \n" +
                "Equipo Caballeros de Elrond: \n" +
                "${listaCaballeros.forEach { a ->  a.mostrarEstado()} } \n" )


//        println("Informe de resultados: \n" +
//                "Equipo Caballeros de Elrond: \n" +
//                "${listaCaballeros.forEach { a -> println( a.mostrarEstado()) }} \n" +
//                "Equipo Amazonas de Isengard: \n" +
//                "${amazonasDeIsengard.sortedBy { it.listaItems.size }} \n")
    }


    private fun reponer() {
        for (i in 0 until tamTablero){
            for (j in 0 until tamTablero){
                tablero.getCasilla(i,j).item ?: tablero.setCasillaItem(listaItems.desapilar(),i, j)
            }
        }
    }

    private fun turnoAmazonas() {
        val jugadorSacado= amazonasDeIsengard.desencolar()
        val fila = (0 until tamTablero).random()
        val columna = (0 until tamTablero).random()
        val itemSelected = tablero.getCasilla(fila,columna).item
        itemSelected?.let { destinado(jugadorSacado, itemSelected, fila, columna) }
        amazonasDeIsengard.encolar(jugadorSacado)
    }

    private fun turnoCaballeros() {
        val jugadorSacado= caballerosDeElrond.desencolar()
        val fila = (0 until tamTablero).random()
        val columna = (0 until tamTablero).random()
        val itemSelected = tablero.getCasilla(fila,columna).item
        itemSelected?.let { destinado(jugadorSacado, itemSelected, fila, columna) }
        caballerosDeElrond.encolar(jugadorSacado)
    }

    private fun destinado(jugadorSacado: Personaje, itemSelected: Item, fila: Int, columna: Int) {
        when{
            itemSelected.tipo==ItemsEnum.COMIDA -> itemComida(itemSelected,jugadorSacado, fila, columna)
            itemSelected.tipo==ItemsEnum.MATERIAL && jugadorSacado is Humanos -> itemMaterial(itemSelected,jugadorSacado, fila, columna)
            itemSelected.tipo==ItemsEnum.POCION && jugadorSacado is Trasgos -> itemPocion(itemSelected,jugadorSacado, fila, columna)
            itemSelected.tipo==ItemsEnum.HECHIZO && jugadorSacado is Elfos -> itemHechizo(itemSelected,jugadorSacado, fila, columna)
        }
    }

    private fun itemHechizo(itemSelected: Item,hechizero: Elfos, fila: Int, columna: Int) {
        hechizero.inmortalidad += 7
        hechizero.setItem(itemSelected)
        tablero.setCasillaItem(null,fila, columna)
    }

    private fun itemPocion(itemSelected: Item,trasgo: Trasgos, fila: Int, columna: Int) {
        trasgo.maldad += 2
        trasgo.setItem(itemSelected)
        tablero.setCasillaItem(null,fila, columna)
    }

    private fun itemMaterial(itemSelected: Item,humano: Humanos, fila: Int, columna: Int) {
        humano.escudo += 5
        humano.setItem(itemSelected)
        tablero.setCasillaItem(null,fila, columna)
    }

    private fun itemComida(itemSelected: Item, jugadorSacado: Personaje, fila: Int, columna: Int) {
        jugadorSacado.setItem(itemSelected)
        tablero.setCasillaItem(null,fila, columna)
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