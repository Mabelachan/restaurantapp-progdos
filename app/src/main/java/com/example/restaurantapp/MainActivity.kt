package com.example.restaurantapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.example.restaurantapp.model.Pedido
import com.example.restaurantapp.model.Plato
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    //Campos de cantidades + switch de propina
    private lateinit var etPastel: EditText
    private lateinit var etCazuela: EditText
    private lateinit var switchPropina: SwitchCompat

    //Textos indicativos
    private lateinit var tvPastelSubtotal: TextView
    private lateinit var tvCazuelaSubtotal: TextView
    private lateinit var tvTotalSinPropina: TextView
    private lateinit var tvPropina: TextView
    private lateinit var tvTotalFinal: TextView

    private val formatoCLP = NumberFormat.getCurrencyInstance(Locale("es", "CL"))

    // Valores iniciales de los platos
    private val pastel = Plato("Pastel de choclo", 12000)
    private val cazuela = Plato("Cazuela", 10000)
    private val pedido = Pedido()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Vincular vistas
        etPastel = findViewById(R.id.etPastelCantidad)
        etCazuela = findViewById(R.id.etCazuelaCantidad)
        switchPropina = findViewById(R.id.switchPropina)

        tvPastelSubtotal = findViewById(R.id.tvPastelSubtotal)
        tvCazuelaSubtotal = findViewById(R.id.tvCazuelaSubtotal)
        tvTotalSinPropina = findViewById(R.id.tvTotalSinPropina)
        tvPropina = findViewById(R.id.tvPropina)
        tvTotalFinal = findViewById(R.id.tvTotalFinal)

        // Agregar platos al pedido
        pedido.agregarPlato(pastel)
        pedido.agregarPlato(cazuela)

        // Para "escuchar" cuando el usuario escribe en el EditText
        etPastel.addTextChangedListener(textWatcher)
        etCazuela.addTextChangedListener(textWatcher)

        switchPropina.setOnCheckedChangeListener { _, isChecked ->
            pedido.incluyePropina = isChecked
            actualizarTotales()
        }
    }

    // mientras el usuario escribe la cantidad se actualiza el total
    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            actualizarTotales()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    private fun actualizarTotales() {
        val cantidadPastel = etPastel.text.toString().toIntOrNull() ?: 0
        val cantidadCazuela = etCazuela.text.toString().toIntOrNull() ?: 0

        pastel.cantidad = cantidadPastel
        cazuela.cantidad = cantidadCazuela

        val subtotalPastel = pastel.calcularSubtotal()
        val subtotalCazuela = cazuela.calcularSubtotal()

        tvPastelSubtotal.text = "Subtotal: ${formatoCLP.format(subtotalPastel)}"
        tvCazuelaSubtotal.text = "Subtotal: ${formatoCLP.format(subtotalCazuela)}"

        val totalSinPropina = pedido.calcularTotalSinPropina()
        val propina = pedido.calcularPropina()
        val totalFinal = pedido.calcularTotalFinal()

        tvTotalSinPropina.text = "Total sin propina: ${formatoCLP.format(totalSinPropina)}"
        tvPropina.text = "Propina: ${formatoCLP.format(propina)}"
        tvTotalFinal.text = "Total final: ${formatoCLP.format(totalFinal)}"
    }
}