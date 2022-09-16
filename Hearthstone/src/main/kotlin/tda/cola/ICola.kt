package tda.cola

abstract class ICola<T>(initialCapacity: Int) : ArrayList<T>(initialCapacity) {
    abstract fun encolar(dato:T)
    abstract fun desencolar():T

}