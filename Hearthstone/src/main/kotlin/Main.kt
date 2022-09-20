import models.tablero.MatrizImpl

fun main(args: Array<String>) {
    val game = Juego(MatrizImpl())
    game.play()
}