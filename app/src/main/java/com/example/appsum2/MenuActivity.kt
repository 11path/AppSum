package com.example.appsum2

import android.location.Location
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MenuActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var usersRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Inicializar FirebaseAuth
        mAuth = FirebaseAuth.getInstance()
        val currentUser: FirebaseUser? = mAuth.currentUser
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()

        // Referenciar la base de datos de usuarios en Firebase
        if (currentUser != null) {
            usersRef = database.getReference("users").child(currentUser.uid)

            // Simular la ubicación GPS (se puede reemplazar con lógica de GPS real)
            val location = Location("dummyprovider")
            location.latitude = -38.7378  // Latitud de Temuco, por ejemplo
            location.longitude = -72.5901  // Longitud de Temuco, por ejemplo

            // Guardar la ubicación en Firebase
            usersRef.child("location").setValue("${location.latitude}, ${location.longitude}")

            // Mostrar la ubicación en la interfaz
            val gpsTextView: TextView = findViewById(R.id.gpsTextView)
            gpsTextView.text = "Ubicación: ${location.latitude}, ${location.longitude}"

            // Calcular el costo de despacho (simulado)
            val montoCompra = 40000.0 // Monto simulado
            val distanciaKm = 10.0 // Distancia simulada en km
            val costoDespacho = calcularCostoDespacho(montoCompra, distanciaKm)

            // Mostrar el costo de despacho en la interfaz
            val costoTextView: TextView = findViewById(R.id.costoTextView)
            costoTextView.text = "Costo de despacho: $$costoDespacho"
        }
    }

    // Función para calcular el costo de despacho
    private fun calcularCostoDespacho(montoCompra: Double, distanciaKm: Double): Double {
        return when {
            montoCompra >= 50000 -> 0.0 // Despacho gratis
            montoCompra >= 25000 -> distanciaKm * 150 // $150 por km
            else -> distanciaKm * 300 // $300 por km
        }
    }
}
