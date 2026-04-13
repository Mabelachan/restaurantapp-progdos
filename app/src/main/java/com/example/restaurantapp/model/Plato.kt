
package com.example.restaurantapp.model

data class Plato(
    val nombre: String,
    val precio: Int,
    var cantidad: Int = 0
) {
    fun calcularSubtotal(): Int {
        return precio * cantidad
    }
}