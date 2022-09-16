package tda.pila

abstract class IPila<T>(initialCapacity: Int) : ArrayList<T>(initialCapacity) {
    abstract fun apilar(dato:T)
    abstract fun desapilar():T

}