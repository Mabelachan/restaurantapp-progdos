
package com.example.restaurantapp.model

class Pedido {
    var incluyePropina: Boolean = false
    private val platos = mutableListOf<Plato>()
    fun agregarPlato(plato: Plato) {
        platos.add(plato)
    }
    fun calcularTotalSinPropina(): Int {
        return platos.sumOf { it.calcularSubtotal() }
    }
    fun calcularPropina(): Int {
        return if (incluyePropina) {
            (calcularTotalSinPropina() * 0.10).toInt()
        } else {
            0
        }
    }
    fun calcularTotalFinal(): Int {
        return calcularTotalSinPropina() + calcularPropina()
    }
}