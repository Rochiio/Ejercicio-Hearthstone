import models.tablero.MatrizImpl

fun main(args: Array<String>) {
    var game = Juego(MatrizImpl())
    game.play()
}